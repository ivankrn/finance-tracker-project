package ru.naumen.enterprisejavacourse.financetracker.view.category;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.service.CategoryService;

@Route("categories/add")
@PermitAll
public class AddCategoryView extends VerticalLayout {

    public AddCategoryView(CategoryService categoryService) {
        H1 header = new H1("Добавить категорию трат");
        add(header);
        TextField name = new TextField("Название");
        add(name);
        Button save = new Button("Сохранить", buttonClickEvent -> {
            categoryService.create(name.getValue());
            UI.getCurrent().navigate(CategoriesView.class);
        });
        add(save);
    }

}
