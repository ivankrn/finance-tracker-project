package ru.naumen.enterprisejavacourse.financetracker.view.transaction;

import com.storedobject.chart.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;
import ru.naumen.enterprisejavacourse.financetracker.service.BankAccountService;
import ru.naumen.enterprisejavacourse.financetracker.service.SecurityService;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;
import ru.naumen.enterprisejavacourse.financetracker.view.bankaccount.BankAccountsView;
import ru.naumen.enterprisejavacourse.financetracker.view.category.AddCategoryView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Route("bank-accounts/:bankAccountId/transactions")
@PermitAll
public class TransactionsView extends VerticalLayout implements BeforeEnterObserver {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final SecurityService securityService;
    private Long bankAccountId;
    private Grid<TransactionDto> grid;
    private DateTimePicker startDatePicker;
    private DateTimePicker endDatePicker;
    private SOChart expenseChart;
    private SOChart incomeChart;

    public TransactionsView(TransactionService transactionService,
                            BankAccountService bankAccountService,
                            SecurityService securityService) {
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.securityService = securityService;
        H1 header = new H1("Транзакции на счету");
        add(header);
        configureFilter();
        configureGrid();
        configureCharts();
        add(new Anchor("javascript:history.back()", "Назад"));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> bankAccountIdParam = event.getRouteParameters().get("bankAccountId");
        if (bankAccountIdParam.isPresent()) {
            this.bankAccountId = Long.valueOf(bankAccountIdParam.get());
            User user = (User) securityService.getAuthenticatedUser();
            if (bankAccountService.findByIdAndUserId(bankAccountId, user.getId())) {
                fillTransactionsList();
                fillCharts();
                add(new RouterLink("Добавить транзакцию", AddTransactionView.class, new RouteParameters(new RouteParam("bankAccountId", bankAccountIdParam.get()))),
                        new RouterLink("Создать категорию", AddCategoryView.class));
            } else {
                event.forwardTo(BankAccountsView.class);
                Notification.show("Счет не найден или доступ запрещен");
            }
        } else {
            event.forwardTo(BankAccountsView.class);
            Notification.show("Некорректный идентификатор счета");
        }
    }

    private void configureFilter() {
        HorizontalLayout filterLayout = new HorizontalLayout();

        startDatePicker = new DateTimePicker("Дата начала");
        endDatePicker = new DateTimePicker("Дата окончания");

        Button filterButton = new Button("Фильтровать", e -> {
            fillTransactionsList();
            fillCharts();
        });

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

    private void configureCharts() {
        expenseChart = new SOChart();
        expenseChart.setSize("800px", "500px");
        incomeChart = new SOChart();
        incomeChart.setSize("800px", "500px");
        add(expenseChart, incomeChart);
    }

    private void fillTransactionsList() {
        List<TransactionDto> transactions;

        LocalDateTime startDate =
                startDatePicker.getValue() != null ? startDatePicker.getValue() : null;
        LocalDateTime endDate =
                endDatePicker.getValue() != null ? endDatePicker.getValue() : null;

        if (startDate != null && endDate != null) {
            transactions = transactionService.findBetweenDatesAndSortByAmount(bankAccountId, startDate, endDate);
        } else {
            transactions = transactionService.findAllForBankAccountId(bankAccountId);
        }

        grid.setItems(transactions);
    }

    private void fillCharts() {
        List<TransactionDto> transactions = transactionService.findAllForBankAccountId(bankAccountId);

        Map<String, BigDecimal> expenseSums = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, TransactionDto::getAmount, BigDecimal::add)
                ));

        Map<String, BigDecimal> incomeSums = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, TransactionDto::getAmount, BigDecimal::add)
                ));

        CategoryData expenseLabels = new CategoryData();
        Data expenseData = new Data();

        expenseSums.forEach((category, amount) -> {
            expenseLabels.add(category);
            expenseData.add(amount.doubleValue());
        });

        PieChart expensePieChart = new PieChart(expenseLabels, expenseData);
        Title expenseTitle = new Title("Расходы по категориям");
        expenseChart.removeAll();
        expenseChart.add(expensePieChart, expenseTitle);

        CategoryData incomeLabels = new CategoryData();
        Data incomeData = new Data();

        incomeSums.forEach((category, amount) -> {
            incomeLabels.add(category);
            incomeData.add(amount.doubleValue());
        });

        PieChart incomePieChart = new PieChart(incomeLabels, incomeData);
        Title incomeTitle = new Title("Доходы по категориям");
        incomeChart.removeAll();
        incomeChart.add(incomePieChart, incomeTitle);
    }
}
