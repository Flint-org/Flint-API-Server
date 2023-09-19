package com.flint.flint.config;

import com.flint.flint.security.auth.CustomAccessDeniedHandler;
import com.flint.flint.security.auth.jwt.JwtAuthenticationEntryPoint;
import com.flint.flint.security.auth.jwt.JwtAuthenticationFilter;
import com.flint.flint.security.auth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/mail/**",
                                "/api/v1/assets/**",
                                "/api/v1/boards/**").permitAll()
                        .requestMatchers("/api/v1/idcard/**").hasRole("AUTHUSER") //"ROLE_"  PREFIX 자동으로 붙여줍니다
                        .requestMatchers(HttpMethod.POST, "/api/v1/clubs/**" ).hasRole("AUTHUSER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/clubs/**").permitAll()
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
