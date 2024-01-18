package springboot.focusing.config.auth.jwt;

import com.fasterxml.jackson.core.JsonParseException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import springboot.focusing.exception.ErrorCode;
import springboot.focusing.exception.auth.ExpiredTokenException;
import springboot.focusing.exception.auth.UnauthorizedException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter implements Filter {
    private final String[] whiteListUris = new String[]{"/user/join"};
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (whiteListCheck(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String header = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

        try {
            tokenProvider.isNotExpiredToken(header);
            chain.doFilter(request, response); // Move this line into try block
        } catch (JsonParseException e) {
            log.error("JsonParseException");
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value());
        } catch (ExpiredJwtException e) {
            log.error("JwtTokenExpired");
            throw new ExpiredTokenException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            log.error("AuthorizationException");
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }
    }

    private boolean whiteListCheck(String uri) {
        return PatternMatchUtils.simpleMatch(whiteListUris, uri);
    }
}
