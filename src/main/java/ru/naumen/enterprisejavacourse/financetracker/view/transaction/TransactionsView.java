package ru.naumen.enterprisejavacourse.financetracker.view.transaction;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;
import ru.naumen.enterprisejavacourse.financetracker.view.category.AddCategoryView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Представление для просмотра совершенных транзакций
 */
@Route("bank-accounts/:bankAccountId/transactions")
@PermitAll
public class TransactionsView extends VerticalLayout implements BeforeEnterObserver {

    private final TransactionService transactionService;
    private Long bankAccountId;
    private Grid<TransactionDto> grid;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    public TransactionsView(TransactionService transactionService) {
        this.transactionService = transactionService;
        H1 header = new H1("Транзакции на счету");
        add(header);
        configureFilter();
        configureGrid();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> bankAccountIdParam = event.getRouteParameters().get("bankAccountId");
        bankAccountIdParam.ifPresent(param -> {
            this.bankAccountId = Long.valueOf(param);
            fillTransactionsList();
            add(new RouterLink("Добавить транзакцию", AddTransactionView.class, new RouteParameters(new RouteParam("bankAccountId", bankAccountId))),
                new RouterLink("Создать категорию", AddCategoryView.class));
        });
    }

    private void configureFilter() {
        HorizontalLayout filterLayout = new HorizontalLayout();

        startDatePicker = new DatePicker("Дата начала");
        endDatePicker = new DatePicker("Дата окончания");

        Button filterButton = new Button("Фильтровать", e -> fillTransactionsList());

        filterLayout.add(startDatePicker, endDatePicker, filterButton);
        add(filterLayout);
    }

    private void configureGrid() {
        grid = new Grid<>(TransactionDto.class, false);
        grid.addColumn(transaction -> transaction.getCategory().getName()).setHeader("Категория транзакции");
        grid.addColumn(TransactionDto::getAmount).setHeader("Сумма транзакции");
        grid.addColumn(TransactionDto::getDate).setHeader("Дата совершения транзакции");
        grid.addComponentColumn(item ->
                new Button("Удалить", e -> {
                    transactionService.deleteTransactionById(item.getId());
                    UI.getCurrent().getPage().reload();
                })
        );
        add(grid);
    }

    private void fillTransactionsList() {
        List<TransactionDto> transactions;

        LocalDateTime startDate =
                startDatePicker.getValue() != null ? startDatePicker.getValue().atStartOfDay() : null;
        LocalDateTime endDate =
                endDatePicker.getValue() != null ? endDatePicker.getValue().atTime(LocalTime.MAX) : null;

        if (startDate != null && endDate != null) {
            transactions = transactionService.findBetweenDatesAndSortByAmount(bankAccountId, startDate, endDate);
        } else {
            transactions = transactionService.findAllForBankAccountId(bankAccountId);
        }

        grid.setItems(transactions);
    }
}

