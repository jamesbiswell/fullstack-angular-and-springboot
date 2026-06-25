package com.luv2code.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JwtConfig {
    
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    
    @Value("${auth0.audience}")
    private String audience;
    
    @Bean
    JwtDecoder jwtDecoder() {
        
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(this.issuer);
        
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(this.audience);
        
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(this.issuer);
        
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(
                withIssuer,
                audienceValidator
        );
        
        jwtDecoder.setJwtValidator(withAudience);
        
        return jwtDecoder;
    }
    
    static class AudienceValidator implements OAuth2TokenValidator<Jwt> {
        
        private final String audience;
        
        AudienceValidator(String audience) {
            this.audience = audience;
        }
        
        @Override
        public OAuth2TokenValidatorResult validate(Jwt jwt) {
            List<String> audiences = jwt.getAudience();
            
            if (audiences.contains(this.audience)) {
                return OAuth2TokenValidatorResult.success();
            }
            
            OAuth2Error error = new OAuth2Error(
                    "invalid_token",
                    "The required audience is missing",
                    null
            );
            
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
