package ru.naumen.enterprisejavacourse.financetracker.view.category;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import ru.naumen.enterprisejavacourse.financetracker.dto.CategoryDto;
import ru.naumen.enterprisejavacourse.financetracker.exception.CategoryDeletionException;
import ru.naumen.enterprisejavacourse.financetracker.service.CategoryService;

import java.util.List;

/**
 * Представление для просмотра категорий
 */
@Route("categories")
@PermitAll
public class CategoriesView extends VerticalLayout {

    private final CategoryService categoryService;

    public CategoriesView(CategoryService categoryService) {
        this.categoryService = categoryService;
        H1 header = new H1("Категории трат");
        add(header);
        fillCategoriesList();
        add(new RouterLink("Добавить категорию", AddCategoryView.class));
        configureBackNavigation();
    }

    private void configureBackNavigation() {
        Button backButton = new Button(
                "Назад", event -> getUI().ifPresent(ui -> ui.getPage().getHistory().back()));
        add(backButton);
    }

    private void fillCategoriesList() {
        List<CategoryDto> categories = categoryService.findAll();
        UnorderedList list = new UnorderedList();
        for (CategoryDto category : categories) {
            ListItem item = new ListItem();
            Span name = new Span(category.getName());
            Icon deleteButton = VaadinIcon.CLOSE_SMALL.create();
            deleteButton.addClickListener(e -> {
                try {
                    categoryService.delete(category.getId());
                    UI.getCurrent().getPage().reload();
                } catch (CategoryDeletionException ex) {
                    Notification.show(ex.getMessage());
                }
            });
            item.add(name, deleteButton);
            list.add(item);
        }
        add(list);
    }

}
