package com.trianglechoke.codesparring.config.security;

import com.trianglechoke.codesparring.member.jwt.JwtAccessDeniedHandler;
import com.trianglechoke.codesparring.member.jwt.JwtAuthenticationEntryPoint;
import com.trianglechoke.codesparring.member.jwt.JwtSecurityConfig;
import com.trianglechoke.codesparring.member.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf(csrf -> csrf.disable())

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )

            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/auth/**","/api/**").permitAll()  // 토큰 없어도 열어주는것
                .anyRequest().authenticated() // 나머지 요청들은 모두 인증 받아야 함
            )

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .headers(headers ->
                headers.frameOptions(options ->
                    options.sameOrigin()
                )
            )
            .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

}