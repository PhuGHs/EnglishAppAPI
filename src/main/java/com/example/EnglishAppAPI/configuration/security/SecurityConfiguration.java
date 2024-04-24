package com.example.EnglishAppAPI.configuration.security;

import com.example.EnglishAppAPI.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Autowired
    public SecurityConfiguration(JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(eh -> eh.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/swagger/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(String.format("%s/auth/**", apiPrefix)).permitAll()
                        .requestMatchers(String.format("%s/english-topics/**", apiPrefix)).permitAll()
                        .requestMatchers(String.format("%s/discussions/**", apiPrefix)).permitAll()
                        .requestMatchers(String.format("%s/interests/**", apiPrefix)).permitAll()

//                        .requestMatchers(String.format("%s/answers/**", apiPrefix)).hasRole(Role.LEARNER)
//                        .requestMatchers(String.format("%s/chat/**", apiPrefix)).hasRole(Role.LEARNER)
//                        .requestMatchers(String.format("%s/discussions/**", apiPrefix)).hasRole(Role.LEARNER)
//                        .requestMatchers(String.format("%s/followers/**", apiPrefix)).hasRole(Role.LEARNER)
//
//                        .requestMatchers(String.format("%s/short-stories/**", apiPrefix)).hasRole(Role.ADMIN)
//
//                        //english-levels
//                        .requestMatchers(HttpMethod.GET, String.format("%s/english-levels", apiPrefix)).hasRole(Role.LEARNER)
//                        .requestMatchers(HttpMethod.POST, String.format("%s/english-levels", apiPrefix)).hasRole(Role.ADMIN)
//
//                        //english-topic
//                        .requestMatchers(HttpMethod.GET, String.format("%s/english-topics/**", apiPrefix)).hasAnyRole(Role.LEARNER, Role.ADMIN)
//                        .requestMatchers(HttpMethod.POST, String.format("%s/english-topics/**", apiPrefix)).hasRole(Role.ADMIN)
//                        .requestMatchers("api/v1/**").permitAll()
                        .anyRequest().authenticated());
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
