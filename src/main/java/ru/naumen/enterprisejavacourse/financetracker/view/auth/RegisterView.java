package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import ru.naumen.enterprisejavacourse.financetracker.service.UserService;

@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView(@Autowired UserService userService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField usernameField = new TextField("Логин");
        PasswordField passwordField = new PasswordField("Пароль");

        Button registerButton = new Button("Зарегистрироваться", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            if (username.isEmpty() || password.isEmpty()) {
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

        add(
                new H1("Регистрация"),
                usernameField,
                passwordField,
                registerButton
        );
    }
}
