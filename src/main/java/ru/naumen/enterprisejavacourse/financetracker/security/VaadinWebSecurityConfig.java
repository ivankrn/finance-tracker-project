package ru.naumen.enterprisejavacourse.financetracker.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.naumen.enterprisejavacourse.financetracker.view.auth.LoginView;

/**
 * Конфигурация веб-приложения с использованием Vaadin
 * Этот класс настраивает безопасность веб-приложения, включая определение страницы входа
 */
@EnableWebSecurity
@Configuration
public class VaadinWebSecurityConfig extends VaadinWebSecurity {

    /**
     * Конфигурирует HTTP безопасность и устанавливает представление для страницы входа
     *
     * @param http объект для конфигурации безопасности
     * @throws Exception если произошла ошибка при настройке безопасности
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }
}
