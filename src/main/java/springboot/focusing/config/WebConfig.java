package springboot.focusing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboot.focusing.config.auth.jwt.JwtAuthorizationFilter;
import springboot.focusing.config.auth.jwt.TokenProviderImpl;

@Configuration
public class WebConfig {

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(TokenProviderImpl provider) {
        return new JwtAuthorizationFilter(provider);
    }
}
