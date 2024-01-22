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

/*
인증이 필요한 경로 설정 및 인증을 위한 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter implements Filter {

    private final String[] whiteListUris = new String[]{"/user/join"};  //인증이 필요 없는 경로
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        /*
        인증이 필요 없는 경로로 요청이 서버로 들어왔다면 아래 인증 과정 무시, 즉 접속가능하도록
         */
        if (whiteListCheck(httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        /*
        요청 헤더를 가져와서 토큰이 유효한 지 확인하고 인허, 유효하지 않다면 에러 발생
         */
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
