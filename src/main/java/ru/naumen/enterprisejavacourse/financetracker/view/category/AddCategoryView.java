package ru.naumen.enterprisejavacourse.financetracker.view.category;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.service.CategoryService;

/**
 * Представление для добавления категорий
 */
@Route("categories/add")
@PermitAll
@CssImport("./style/add-category-style.css")
public class AddCategoryView extends VerticalLayout {

    public AddCategoryView(CategoryService categoryService) {
        addClassName("add-category-view");
        setWidth("auto");

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.addClassName("add-category-layout");

        H1 header = new H1("Добавить категорию трат");
        contentLayout.add(header);

        TextField name = new TextField("Название");
        contentLayout.add(name);
        Button save = new Button("Сохранить", buttonClickEvent -> {
            categoryService.create(name.getValue());
            UI.getCurrent().navigate(CategoriesView.class);
        });
        contentLayout.add(save);
        contentLayout.add(configureBackNavigation());
        add(contentLayout);
    }

    private Button configureBackNavigation() {
        return new Button(
                "Назад", event -> getUI().ifPresent(ui -> ui.getPage().getHistory().back()));
    }

}
