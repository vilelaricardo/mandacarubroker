package com.mandacarubroker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] ADM_URLS={"/users/**"};
    private static final String[] NORMAL_URLS={"/stocks/**","/login/**"};
    private static final String[] PUBLIC_URLS={"/login/**","/users/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,TokenSecurityFilter filter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x->x.requestMatchers(HttpMethod.POST,PUBLIC_URLS).permitAll())
                .authorizeHttpRequests(x->x.requestMatchers(HttpMethod.GET,NORMAL_URLS).permitAll())
                .authorizeHttpRequests(x->x.requestMatchers(ADM_URLS).hasAuthority("ADMIN"))
                .authorizeHttpRequests(x->x.requestMatchers(NORMAL_URLS).hasAuthority("ADMIN"))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
