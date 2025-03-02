package com.company.configuration;

import com.company.filters.JwtTokenFilter;
import com.company.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http
                .csrf(AbstractHttpConfigurer::disable)
                 .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                 .authorizeHttpRequests(request->{
                     request.requestMatchers(
                             String.format("%s/users/register",apiPrefix),
                             String.format("%s/users/login",apiPrefix)
                     ).permitAll()
                             .requestMatchers(GET,
                                     String.format("%s/products/**",apiPrefix)).hasAnyRole(Role.USER,Role.ADMIN)

                             .requestMatchers(POST,
                                     String.format("%s/products/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(PUT,
                                     String.format("%s/products/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(DELETE,
                                     String.format("%s/products/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(GET,
                                     String.format("%s/categories/**",apiPrefix)).hasAnyRole(Role.USER,Role.ADMIN)

                             .requestMatchers(POST,
                                     String.format("%s/categories/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(PUT,
                                     String.format("%s/categories/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(DELETE,
                                     String.format("%s/categories/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(GET,
                                     String.format("%s/orders/**",apiPrefix)).hasAnyRole(Role.USER,Role.ADMIN)

                             .requestMatchers(POST,
                                     String.format("%s/orders/**",apiPrefix)).hasRole(Role.USER)

                             .requestMatchers(PUT,
                                     String.format("%s/orders/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(DELETE,
                                     String.format("%s/orders/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(GET,
                                     String.format("%s/order_details/**",apiPrefix)).hasAnyRole(Role.USER,Role.ADMIN)

                             .requestMatchers(POST,
                                     String.format("%s/order_details/**",apiPrefix)).hasRole(Role.USER)

                             .requestMatchers(PUT,
                                     String.format("%s/order_details/**",apiPrefix)).hasRole(Role.ADMIN)

                             .requestMatchers(DELETE,
                                     String.format("%s/order_details/**",apiPrefix)).hasRole(Role.ADMIN)

                             .anyRequest().authenticated();
                 });

         return http.build();
    }
}
