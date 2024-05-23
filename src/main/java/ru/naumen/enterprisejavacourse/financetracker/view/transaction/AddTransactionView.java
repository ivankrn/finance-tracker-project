package ru.naumen.enterprisejavacourse.financetracker.view.transaction;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.dto.CategoryDto;
import ru.naumen.enterprisejavacourse.financetracker.service.CategoryService;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;
import ru.naumen.enterprisejavacourse.financetracker.view.bankaccount.BankAccountsView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Представление для добавления транзакций
 */
@Route("bank-accounts/:bankAccountId?/transactions/add")
@PermitAll
public class AddTransactionView extends VerticalLayout implements BeforeEnterObserver {

    private final CategoryService categoryService;
    private final TransactionService transactionService;
    private Long bankAccountId;

    public AddTransactionView(TransactionService transactionService,
                              CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        H1 header = new H1("Добавить транзакцию");
        add(header);
        configureBackNavigation();
    }

    private void configureBackNavigation() {
        Button backButton = new Button(
                "Назад", event -> getUI().ifPresent(ui -> ui.getPage().getHistory().back()));
        add(backButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> bankAccountIdParam = event.getRouteParameters().get("bankAccountId");
        bankAccountIdParam.ifPresent(param -> {
            this.bankAccountId = Long.valueOf(param);
            addForm();
        });
    }

    private void addForm() {
        RadioButtonGroup<String> typeRadioButtonGroup = new RadioButtonGroup<>();
        typeRadioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        typeRadioButtonGroup.setLabel("Тип транзакции");
        typeRadioButtonGroup.setItems("Пополнение", "Трата");
        ComboBox<CategoryDto> categoryComboBox = new ComboBox<>("Категория");
        categoryComboBox.setItems(categoryService.findAll());
        categoryComboBox.setItemLabelGenerator(CategoryDto::getName);
        TextField amountField = new TextField("Сумма");
        DatePicker datePicker = new DatePicker("Дата совершения транзакции");
        add(typeRadioButtonGroup, categoryComboBox, amountField, datePicker);
        Button saveButton = new Button("Сохранить", buttonClickEvent -> {
            long categoryId = categoryComboBox.getValue().getId();
            BigDecimal amount = new BigDecimal(amountField.getValue());
            LocalDateTime date = LocalDateTime.of(datePicker.getValue(), LocalTime.of(0, 0));
            if (typeRadioButtonGroup.getValue().equals("Пополнение")) {
                transactionService.accrual(bankAccountId, categoryId, amount, date);
            } else {
                transactionService.withdraw(bankAccountId, categoryId, amount, date);
            }
            UI.getCurrent().navigate(BankAccountsView.class);
        });
        add(saveButton);
    }

}
