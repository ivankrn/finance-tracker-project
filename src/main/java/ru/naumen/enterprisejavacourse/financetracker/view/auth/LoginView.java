package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Представление для входа в систему
 */
@Route("login")
@AnonymousAllowed
@CssImport("./style/login-style.css")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 welcomeMessage = new H1("Добро пожаловать на платформу SMARTMONEY!");
        welcomeMessage.addClassName("welcome-message");

        LoginForm login = new LoginForm();
        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);
        login.addClassName("login-form");

        Anchor registerLink = new Anchor("register", "Регистрация");
        registerLink.addClassName("register-link");

        VerticalLayout loginLayout = new VerticalLayout(welcomeMessage, login, registerLink);
        loginLayout.setAlignItems(Alignment.CENTER);
        loginLayout.addClassName("login-layout");

        add(loginLayout);

        addClassName("login-view");
    }
}
