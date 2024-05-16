package ru.naumen.enterprisejavacourse.financetracker.view.transaction;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;
import ru.naumen.enterprisejavacourse.financetracker.service.SecurityService;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;

import java.util.List;
import java.util.Optional;

@Route("bank-accounts/:bankAccountId/transactions")
@PermitAll
public class TransactionsView extends VerticalLayout implements BeforeEnterObserver {

    private final SecurityService securityService;
    private final TransactionService transactionService;
    private Long bankAccountId;

    public TransactionsView(SecurityService securityService, TransactionService transactionService) {
        this.securityService = securityService;
        this.transactionService = transactionService;
        H1 header = new H1("Транзакции на счету");
        add(header);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> bankAccountIdParam = event.getRouteParameters().get("bankAccountId");
        bankAccountIdParam.ifPresent(param -> {
            this.bankAccountId = Long.valueOf(param);
            fillTransactionsList();
            add(new RouterLink("Добавить транзакцию", AddTransactionView.class,
                    new RouteParameters(new RouteParam("bankAccountId", bankAccountId))));
        });
    }

    private void fillTransactionsList() {
        List<TransactionDto> transactions = transactionService.findAllForBankAccountId(bankAccountId);
        Grid<TransactionDto> grid = new Grid<>(TransactionDto.class, false);
        grid.addColumn(transaction -> transaction.getCategory().getName()).setHeader("Категория транзакции");
        grid.addColumn(TransactionDto::getAmount).setHeader("Сумма транзакции");
        grid.addColumn(TransactionDto::getDate).setHeader("Дата совершения транзакции");
        grid.addComponentColumn(item ->
                new Button("Удалить", e -> {
                    transactionService.deleteTransactionById(item.getId());
                    UI.getCurrent().getPage().reload();
                })
        );
        grid.setItems(transactions);
        add(grid);
    }
}
