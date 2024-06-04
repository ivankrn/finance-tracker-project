package ru.naumen.enterprisejavacourse.financetracker.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.naumen.enterprisejavacourse.financetracker.service.BankAccountService;
import ru.naumen.enterprisejavacourse.financetracker.service.SecurityService;
import ru.naumen.enterprisejavacourse.financetracker.view.bankaccount.BankAccountsView;

/**
 * Главное представление
 * Если пользователь аутентифицирован, предоставляет возможность выхода из системы
 */
@Route("")
@PermitAll
public class MainView extends VerticalLayout {

    public MainView(SecurityService securityService, BankAccountService bankAccountService) {
        H1 logo = new H1("Финансовый дневник");
        VerticalLayout logoLayout = new VerticalLayout(logo);
        logoLayout.setAlignItems(Alignment.CENTER);
        logo.addClassName("logo");
        VerticalLayout header;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null && securityService.getAuthenticatedUser() != null) {
            header = new VerticalLayout(logoLayout);

            Button logout = new Button("Выйти", click -> securityService.logout());
            header.add(new BankAccountsView(securityService, bankAccountService));
            header.add(logout);
        } else {
            header = new VerticalLayout(logo);
        }

        add(header);
    }
}
