package io.fresh.burger.auth.service.core.config.security;

import io.fresh.burger.auth.service.core.filter.JwtAuthenticationFilter;
import io.fresh.burger.auth.service.core.service.ClientSecurityDetailsService;
import io.fresh.burger.auth.service.core.service.JwtService;
import io.fresh.burger.auth.service.core.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final ClientSecurityDetailsService clientSecurityDetailsService;

    /**
     * Конфигурация фильтров безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF, если используем JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AppConstants.API_VERSION + "/auth/**").permitAll() // Разрешить доступ к эндпоинтам под /auth/**
                        .anyRequest().authenticated() // Остальные запросы требуют аутентификации
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Добавляем фильтр для проверки JWT
        return http.build();
    }

    /**
     * Фильтр для проверки JWT токенов
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, clientSecurityDetailsService);
    }

    /**
     * Определение AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Настройка PasswordEncoder для хеширования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(clientSecurityDetailsService); // Ваш UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Ваш PasswordEncoder
        return authProvider;
    }
}
