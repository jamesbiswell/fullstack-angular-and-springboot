package com.luv2code.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // protect endpoint /api/orders
        http.authorizeRequests(configurer ->
                        configurer
                                .antMatchers("/api/orders/**")
                                .authenticated())
                .oauth2ResourceServer()
                .jwt();

        // add CORS filters
        http.cors();

        // add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class,
                             new HeaderContentNegotiationStrategy());

        // force a non-empty response body for 401's to make the response more friendly
        Okta.configureResourceServer401ResponseBody(http);

        /*
        - Otherwise checkout request fails with HTTP POST
        - By default CSRF is enabled for all state-changing methods (POST, PUT, DELETE, PATCH)
        - CSRF performs checks on POST using cookies
        - Since the Angular app is not using cookies for session tracking, CSRF says request is unauthorised
        - Can be resolved by disabling CSRF
        - This technique is commonly used for REST APIs where it is stateless and authentication is done using tokens instead of cookies
         */
        
        // disable CSRF since we are not using Cookies for session tracking
        http.csrf().disable();

        return http.build();
    }
}







