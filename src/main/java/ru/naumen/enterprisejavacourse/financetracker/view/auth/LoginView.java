package ru.naumen.enterprisejavacourse.financetracker.view.auth;

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
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm login = new LoginForm();
        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);
        add(new H1("Вход"), login);
    }

}
