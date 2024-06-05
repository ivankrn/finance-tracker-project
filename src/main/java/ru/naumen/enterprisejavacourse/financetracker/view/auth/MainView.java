package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
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
@CssImport("./style/main-style.css")
public class MainView extends VerticalLayout {

    public MainView(SecurityService securityService) {
        H1 logo = new H1("Финансовый дневник");
        logo.addClassName("logo");
        VerticalLayout header;
        setSizeFull();
        addClassName("main-view");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null && securityService.getAuthenticatedUser() != null) {
            H1 welcomeMessage = new H1("Добро пожаловать, " + username);
            header = new VerticalLayout(logo, welcomeMessage);
            header.addClassName("main-layout");

            RouterLink bankAccountLink = new RouterLink("Банковские счета", BankAccountsView.class);
            bankAccountLink.addClassName("main-link");

            RouterLink categoriesLink = new RouterLink("Категории трат", CategoriesView.class);
            categoriesLink.addClassName("main-link");

            Div linksDiv = new Div();
            linksDiv.addClassName("links-div");
            linksDiv.add(bankAccountLink);
            linksDiv.add(categoriesLink);

            header.add(linksDiv);

            Button logout = new Button("Выйти", click -> securityService.logout());
            header.add(logout);
        } else {
            header = new VerticalLayout(logo);
        }

        add(header);

    }
}
