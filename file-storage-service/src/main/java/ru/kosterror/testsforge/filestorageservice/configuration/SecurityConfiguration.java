package ru.kosterror.testsforge.filestorageservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import ru.kosterror.testsforge.securitystarter.security.ApiKeyAuthenticationConverter;
import ru.kosterror.testsforge.securitystarter.security.ApiKeyAuthenticationFilter;
import ru.kosterror.testsforge.securitystarter.security.JwtAuthenticationConverter;
import ru.kosterror.testsforge.securitystarter.security.JwtAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String API_PATTERN = "/api/**";

    private final JwtAuthenticationConverter authenticationConverter;
    private final ApiKeyAuthenticationConverter apiKeyAuthenticationConverter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                ).sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                                antMatcher(HttpMethod.GET, "/api/files/*"),
                                antMatcher(HttpMethod.POST, "/api/files/*")
                        ).hasAnyRole("STUDENT", "TEACHER", "SERVICE")
                        .requestMatchers(
                                new NegatedRequestMatcher(antMatcher(API_PATTERN))
                        ).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(
                        authenticationConverter,
                        antMatcher(API_PATTERN)
                ), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new ApiKeyAuthenticationFilter(
                        apiKeyAuthenticationConverter,
                        antMatcher(API_PATTERN)
                ), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
