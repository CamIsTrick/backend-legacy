package springboot.focusing.config.auth.jwt;

import lombok.Getter;

@Getter
public final class JwtProperties {
    public static final String SECRET = "your_secret_key_your_secret_key_your_secret_key_your_secret_key";
    public static final int EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}