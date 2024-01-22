package springboot.focusing.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.focusing.config.auth.dto.MemberInfoForToken;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProviderImpl implements TokenProvider {

    //재발급 토큰 생성
    @Override
    public String generateRefreshToken(MemberInfoForToken memberInfoForToken) {
        Claims claims = getClaimsFrom(memberInfoForToken);
        return getTokenFrom(claims, JwtProperties.EXPIRATION_TIME);
    }

    private Claims getClaimsFrom(MemberInfoForToken memberInfoForToken) {
        Claims claims = Jwts.claims();
        claims.put("name", memberInfoForToken.name());
        return claims;
    }

    private String getTokenFrom(Claims claims, long validTime) {
        Date now = new Date();
        return Jwts.builder().setHeaderParam("type", "JWT").setClaims(claims).setIssuedAt(now).setExpiration(new Date(now.getTime() + validTime)).signWith(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes()), SignatureAlgorithm.HS256).compact();
    }

    //토큰 생성
    @Override
    public String generateAccessToken(MemberInfoForToken memberInfoForToken) {
        Claims claims = getClaimsFrom(memberInfoForToken);
        return getTokenFrom(claims, JwtProperties.EXPIRATION_TIME);
    }

    //유효한 토큰인지 확인
    @Override
    public boolean isNotExpiredToken(String token) {
        try {
            return isNotExpired(stripBearerPrefix(token));
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    private boolean isNotExpired(String token) {
        return !Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes())).build().parseClaimsJws(stripBearerPrefix(token)).getBody().getExpiration().before(new Date());
    }

    //토큰에서 사용자 정보 가져오기
    @Override
    public MemberInfoForToken getMemberInfoFromToken(String accessToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes())).build().parseClaimsJws(stripBearerPrefix(accessToken)).getBody();

        return new MemberInfoForToken((String) claims.get("name"));
    }

    private String stripBearerPrefix(String token) {
        if (token != null && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return token.substring(7);
        }
        return token;
    }
}