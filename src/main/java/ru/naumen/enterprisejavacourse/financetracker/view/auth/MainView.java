package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.naumen.enterprisejavacourse.financetracker.view.auth.security.SecurityService;

@Route("")
@PermitAll
public class MainView extends VerticalLayout {

    public MainView(@Autowired SecurityService securityService) {

        H1 logo = new H1("Главная страница");
        logo.addClassName("logo");
        HorizontalLayout header;
        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Выйти", click ->
                    securityService.logout());
            header = new HorizontalLayout(logo, logout);
        } else {
            header = new HorizontalLayout(logo);
        }


        add(header);
    }
}
