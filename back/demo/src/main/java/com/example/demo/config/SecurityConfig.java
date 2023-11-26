package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationEntryPoint;
import com.example.demo.security.JwtAuthenticationFilter;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final AdminServerProperties adminServer;

    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder(4);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return encoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return builder.build();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                // Requests
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/checkAuth").authenticated()
                                .anyRequest().permitAll()
                )
//                .antMatchers(adminServer.path("/assets/**")).permitAll()
//                .antMatchers(adminServer.path("/actuator/info")).permitAll()
//                .antMatchers(adminServer.path("/actuator/health")).permitAll()
//                .antMatchers(adminServer.path("/login")).permitAll()
                // Exceptions
                .exceptionHandling(it -> it.authenticationEntryPoint(entryPoint))
                // Disable sessions
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Custom filters
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
