package ru.naumen.enterprisejavacourse.financetracker.view.transaction;

import com.storedobject.chart.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Представление для отображения транзакций
 */
@Route("bank-accounts/:bankAccountId/transactions")
@PermitAll
@CssImport("./style/transactions-style.css")
public class TransactionsView extends VerticalLayout implements BeforeEnterObserver {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final SecurityService securityService;
    private Long bankAccountId;
    private final RouterLink addTransactionLink = new RouterLink();
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
        setClassName("transactions-view");
        setWidth("auto");
        configureFilter();
        configureGrid();
        configureCharts();
        configureBackNavigation();
    }

    private void configureBackNavigation() {
        Button backButton = new Button("Назад", event -> getUI().ifPresent(ui -> ui.getPage().getHistory().back()));
        add(backButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> bankAccountIdParam = event.getRouteParameters().get("bankAccountId");
        if (bankAccountIdParam.isPresent()) {
            this.bankAccountId = Long.valueOf(bankAccountIdParam.get());
            User user = (User) securityService.getAuthenticatedUser();
            if (bankAccountService.hasBankAccountWithId(bankAccountId, user.getId())) {
                try {
                    updateView();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                addTransactionLink.setRoute(AddTransactionView.class,
                        new RouteParameters(new RouteParam("bankAccountId", bankAccountIdParam.get())));
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
        filterLayout.setAlignItems(Alignment.END);
        startDatePicker = new DateTimePicker("Дата начала");
        endDatePicker = new DateTimePicker("Дата окончания");

        Button filterButton = new Button("Фильтровать", e -> {
            try {
                updateView();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        filterLayout.add(startDatePicker, endDatePicker, filterButton);
        add(filterLayout);
    }

    private void configureGrid() {
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.setMargin(true);
        grid = new Grid<>(TransactionDto.class, false);
        grid.addColumn(transaction -> transaction.getCategory().getName()).setHeader("Категория транзакции");
        grid.addColumn(TransactionDto::getAmount).setHeader("Сумма транзакции");
        grid.addColumn(TransactionDto::getDate).setHeader("Дата совершения транзакции");
        grid.addComponentColumn(item ->
                new Button("Удалить", e -> {
                    transactionService.deleteTransactionById(item.getId());
                    try {
                        updateView();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        addTransactionLink.setText("Добавить транзакцию");
        gridLayout.add(grid, addTransactionLink);
        add(gridLayout);
    }

    private void configureCharts() {
        HorizontalLayout charts = new HorizontalLayout();
        expenseChart = new SOChart();
        expenseChart.setSize("800px", "500px");
        incomeChart = new SOChart();
        incomeChart.setSize("800px", "500px");
        charts.add(expenseChart, incomeChart);
        add(charts);
    }

    private void updateView() throws Exception {
        List<TransactionDto> transactions = getFilteredTransactions();
        fillTransactionsList(transactions);
        fillCharts(transactions);
    }

    private List<TransactionDto> getFilteredTransactions() {
        LocalDateTime startDate = startDatePicker.getValue();
        LocalDateTime endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null) {
            return transactionService.findBetweenDatesAndSortByAmount(bankAccountId, startDate, endDate);
        } else {
            return transactionService.findAllForBankAccountId(bankAccountId);
        }
    }

    private void fillTransactionsList(List<TransactionDto> transactions) {
        grid.setItems(transactions);
    }

    private void fillCharts(List<TransactionDto> transactions) throws Exception {
        Map<String, BigDecimal> expenseSums = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, t -> t.getAmount().abs(), BigDecimal::add)
                ));

        Map<String, BigDecimal> incomeSums = transactions.stream()
                .filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, TransactionDto::getAmount, BigDecimal::add)
                ));

        updateChart(expenseChart, "Расходы по категориям", expenseSums);
        updateChart(incomeChart, "Доходы по категориям", incomeSums);
    }

    private void updateChart(SOChart chart, String titleText, Map<String, BigDecimal> data) throws Exception {
        CategoryData labels = new CategoryData();
        Data chartData = new Data();

        data.forEach((category, amount) -> {
            labels.add(category);
            chartData.add(amount.doubleValue());
        });

        PieChart pieChart = new PieChart(labels, chartData);
        pieChart.setName(titleText);
        Title title = new Title(titleText);

        chart.removeAll();
        chart.add(pieChart);
        chart.add(title);
        chart.update();
    }
}
