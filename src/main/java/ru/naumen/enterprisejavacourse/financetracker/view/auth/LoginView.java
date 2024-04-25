package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Представление для входа в систему
 */
@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private static final String MAIN_PAGE_URL = "";

    public LoginView(AuthenticationManager authenticationManager) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField usernameField = new TextField("Логин");
        PasswordField passwordField = new PasswordField("Пароль");

        Button loginButton = new Button("Войти", event -> {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(usernameField.getValue(), passwordField.getValue())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UI.getCurrent().navigate(MAIN_PAGE_URL);
            } catch (AuthenticationException e) {
                Notification.show(
                        "Ошибка входа: " + e.getMessage(),
                        3000,
                        Notification.Position.TOP_CENTER);
            }
        });

        add(
                new H1("Финансовый дневник"),
                usernameField,
                passwordField,
                loginButton
        );
    }
}
