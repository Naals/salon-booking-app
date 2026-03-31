package com.project.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;



@Configuration
public class SecurityConfig {

    private static final String[] FREE_RESOURCE_URLS = {
            "/api/salons/**",
            "/api/categories/**",
            "/api/notifications/**",
            "/api/bookings/**",
            "/api/payments/**",
            "/api/service-offering/**",
            "/api/users/**",
            "/api/reviews/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges -> exchanges
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/api/notifications/ws/**").permitAll()
                .pathMatchers(
                        FREE_RESOURCE_URLS
                ).hasAnyRole("CUSTOMER", "SALON_OWNER", "ADMIN").pathMatchers(
                                        "/api/categories/salon-owner/**",
                                        "/api/notifications/salon-owner/**",
                                        "/api/service-offering/salon-owner/**"
                                ).hasAnyRole("SALON_OWNER")
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantAuthoritiesExtractor()))
                );

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    private Converter<Jwt,? extends Mono<? extends AbstractAuthenticationToken>> grantAuthoritiesExtractor() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                new KeycloakRoleConverter()
        );

        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }

}
