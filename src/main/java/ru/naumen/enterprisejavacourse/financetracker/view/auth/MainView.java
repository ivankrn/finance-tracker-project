package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.naumen.enterprisejavacourse.financetracker.service.SecurityService;
import ru.naumen.enterprisejavacourse.financetracker.view.bankaccount.BankAccountsView;
import ru.naumen.enterprisejavacourse.financetracker.view.category.CategoriesView;

/**
 * Главное представление
 * Если пользователь аутентифицирован, предоставляет возможность выхода из системы
 */
@Route("")
@PermitAll
public class MainView extends VerticalLayout {

    public MainView(SecurityService securityService) {
        H1 logo = new H1("Финансовый дневник");
        logo.addClassName("logo");

        VerticalLayout header;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null && securityService.getAuthenticatedUser() != null) {
            H1 welcomeMessage = new H1("Добро пожаловать, " + username);
            header = new VerticalLayout(logo, welcomeMessage);

            Button logout = new Button("Выйти", click -> securityService.logout());
            header.add(logout);
            header.add(new RouterLink("Банковские счета", BankAccountsView.class));
            header.add(new RouterLink("Категории трат", CategoriesView.class));
        } else {
            header = new VerticalLayout(logo);
        }

        add(header);
    }
}
