package springboot.focusing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboot.focusing.config.auth.jwt.JwtAuthorizationFilter;
import springboot.focusing.config.auth.jwt.TokenProviderImpl;

@Configuration
public class WebConfig {

    // 모든 웹서버 요청은 인증 필터를 거치도록 설정
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(TokenProviderImpl provider) {
        return new JwtAuthorizationFilter(provider);
    }
}
