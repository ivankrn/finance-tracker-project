package ru.naumen.enterprisejavacourse.financetracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.UserRepository;

/**
 * Конфигурация безопасности приложения
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    /**
     * Возвращает сервис данных о пользователях
     *
     * @return сервис информации о пользователях
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    /**
     * Возвращает поставщика аутентификации
     *
     * @return поставщик аутентификации
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Возвращает менеджер аутентификации
     *
     * @param config конфигурация аутентификации
     * @return менеджер аутентификации
     * @throws Exception при ошибке аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Возвращает кодировщик паролей
     *
     * @return кодировщик паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
