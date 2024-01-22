package springboot.focusing.config.auth.jwt;

import lombok.Getter;

@Getter
public final class JwtProperties {
    public static final String SECRET = "your_secret_key_your_secret_key_your_secret_key_your_secret_key";  //비밀키
    public static final int EXPIRATION_TIME = 24 * 60 * 60 * 1000;  //만료시간
    public static final String TOKEN_PREFIX = "Bearer ";    //토큰 시작 접두사
    public static final String HEADER_STRING = "Authorization"; //헤더 속성이름
}