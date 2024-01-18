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
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(Keys.hmacShaKeyFor(
                                JwtProperties.SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    @Override
    public String generateAccessToken(MemberInfoForToken memberInfoForToken) {
        Claims claims = getClaimsFrom(memberInfoForToken);
        return getTokenFrom(claims, JwtProperties.EXPIRATION_TIME);
    }

    @Override
    public boolean isNotExpiredToken(String token) {
        try {
            return isNotExpired(stripBearerPrefix(token));
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    private boolean isNotExpired(String token) {
        return !Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes()))
                .build()
                .parseClaimsJws(stripBearerPrefix(token))
                .getBody()
                .getExpiration().before(new Date());
    }

    @Override
    public MemberInfoForToken getMemberInfoFromToken(String accessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes()))
                .build()
                .parseClaimsJws(stripBearerPrefix(accessToken))
                .getBody();

        return new MemberInfoForToken(
                (String) claims.get("name")
        );
    }

    private String stripBearerPrefix(String token) {
        if (token != null && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return token.substring(7);
        }
        return token;
    }
}