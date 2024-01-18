package springboot.focusing.config.auth.jwt;

import springboot.focusing.config.auth.dto.MemberInfoForToken;

public interface TokenProvider {
    String generateAccessToken(MemberInfoForToken memberInfoForToken);

    String generateRefreshToken(MemberInfoForToken memberInfoForToken);

    boolean isNotExpiredToken(String token);

    MemberInfoForToken getMemberInfoFromToken(String accessToken);
}