package org.as.devtechsolution.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Aditya Srivastva
 */

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(exchanges ->

                        exchanges.pathMatchers(HttpMethod.GET).permitAll()
//                                .pathMatchers("/bankapp/accounts/**").authenticated()
//                                .pathMatchers("/bankapp/cards/**").authenticated()
//                                .pathMatchers("/bankapp/loans/**").authenticated()
                                .pathMatchers("/bankapp/accounts/**").hasRole("ACCOUNTS")
                                .pathMatchers("/bankapp/cards/**").hasRole("CARDS")
                                .pathMatchers("/bankapp/loans/**").hasRole("LOANS")

                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec ->
                        //oAuth2ResourceServerSpec.jwt(Customizer.withDefaults())
                        oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
                );
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return serverHttpSecurity.build();
    }

    Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();;
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());


        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
