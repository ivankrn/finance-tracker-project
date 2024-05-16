package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import ru.naumen.enterprisejavacourse.financetracker.service.UserService;

/**
 * Представление для регистрации пользователя
 */
@Route("register")
@AnonymousAllowed
@CssImport("./style/register-style.css")
public class RegisterView extends VerticalLayout {

    public RegisterView(UserService userService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 welcomeMessage = new H1("Добро пожаловать на платформу SMARTMONEY!");
        welcomeMessage.addClassName("welcome-message");

        TextField usernameField = new TextField("Логин");
        usernameField.addClassName("username-field");

        PasswordField passwordField = new PasswordField("Пароль");
        passwordField.addClassName("password-field");

        Button registerButtonMain = new Button("Регистрация", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            if (username.isBlank() || password.isBlank()) {
                Notification.show("Пожалуйста, введите логин и пароль");
            } else {
                try {
                    userService.register(username, password);
                    Notification.show("Вы успешно зарегистрировались");
                } catch (Exception e) {
                    Notification.show("Ошибка регистрации: " + e.getMessage());
                }
            }
        });
        registerButtonMain.addClassName("register-button-main");

        Anchor loginLink = new Anchor("login", "Войти");
        loginLink.addClassName("login-link");

        VerticalLayout registerLayout = new VerticalLayout(
                welcomeMessage, usernameField, passwordField, registerButtonMain, loginLink);
        registerLayout.setAlignItems(Alignment.CENTER);
        registerLayout.addClassName("register-layout");

        add(registerLayout);

        addClassName("register-view");
    }
}
