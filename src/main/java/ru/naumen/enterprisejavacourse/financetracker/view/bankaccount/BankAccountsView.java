package ru.naumen.enterprisejavacourse.financetracker.view.bankaccount;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;
import ru.naumen.enterprisejavacourse.financetracker.dto.BankAccountDto;
import ru.naumen.enterprisejavacourse.financetracker.exception.BankAccountDeletionException;
import ru.naumen.enterprisejavacourse.financetracker.service.BankAccountService;
import ru.naumen.enterprisejavacourse.financetracker.service.SecurityService;
import ru.naumen.enterprisejavacourse.financetracker.view.transaction.TransactionsView;

import java.util.List;

/**
 * Представление для просмотра банковских счетов
 */
@Route("bank-accounts")
@PermitAll
public class BankAccountsView extends VerticalLayout {

    private final SecurityService securityService;
    private final BankAccountService bankAccountService;

    public BankAccountsView(SecurityService securityService, BankAccountService bankAccountService) {
        this.securityService = securityService;
        this.bankAccountService = bankAccountService;
        H1 header = new H1("Счета пользователя");
        add(header);
        fillAccountsList();
        add(new RouterLink("Добавить счет", AddBankAccountView.class));
        configureBackNavigation();
    }

    private void configureBackNavigation() {
        Button backButton = new Button(
                "Назад", event -> getUI().ifPresent(ui -> ui.getPage().getHistory().back()));
        add(backButton);
    }

    private void fillAccountsList() {
        User user = (User) securityService.getAuthenticatedUser();
        List<BankAccountDto> bankAccounts = bankAccountService.findAllForUserId(user.getId());
        Grid<BankAccountDto> grid = new Grid<>(BankAccountDto.class, false);
        grid.addColumn(BankAccountDto::getName).setHeader("Название счета");
        grid.addColumn(BankAccountDto::getAmount).setHeader("Сумма счета");
        grid.addColumn(BankAccountDto::getCurrency).setHeader("Валюта счета");
        grid.addComponentColumn(item ->
                    new Button("Посмотреть операции", e -> UI.getCurrent().navigate(
                            TransactionsView.class,
                            new RouteParameters(new RouteParam("bankAccountId", String.valueOf(item.getId())))))
        );
        grid.addComponentColumn(item ->
                new Button("Удалить", e -> {
                    try {
                        bankAccountService.delete(item.getId());
                        UI.getCurrent().getPage().reload();
                    } catch (BankAccountDeletionException ex) {
                        Notification.show(ex.getMessage());
                    }
                })
        );
        grid.setItems(bankAccounts);
        add(grid);
    }

}
