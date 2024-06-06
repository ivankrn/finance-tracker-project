package ru.naumen.enterprisejavacourse.financetracker.view.bankaccount;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;
import ru.naumen.enterprisejavacourse.financetracker.service.BankAccountService;
import ru.naumen.enterprisejavacourse.financetracker.service.SecurityService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Представление для добавления банковских счетов
 */
@Route("bank-accounts/add")
@PermitAll
@CssImport("./style/add-bank-account-style.css")
public class AddBankAccountView extends VerticalLayout {

    public AddBankAccountView(SecurityService securityService, BankAccountService bankAccountService) {
        User user = (User) securityService.getAuthenticatedUser();
        addClassName("add-bank-account-view");
        setWidth("auto");

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.addClassName("add-bank-account-layout");

        H1 header = new H1("Добавить счет");
        contentLayout.add(header);

        TextField name = new TextField("Название");
        TextField amount = new TextField("Сумма");
        ComboBox<String> currency = new ComboBox<>("Валюта");
        currency.setItems(List.of("RUB", "USD", "EUR", "CNY"));
        contentLayout.add(name, amount, currency);
        Button saveButton = new Button("Сохранить", buttonClickEvent -> {
            bankAccountService.addForUser(
                    user,
                    name.getValue(),
                    new BigDecimal(amount.getValue()),
                    currency.getValue()
            );
            UI.getCurrent().navigate(BankAccountsView.class);
        });
        contentLayout.add(saveButton);
        contentLayout.add(configureBackNavigation());

        add(contentLayout);
    }

    private Button configureBackNavigation() {
        return new Button(
                "Назад", event -> getUI().ifPresent(ui -> ui.getPage().getHistory().back()));
    }
}
