import lombok.Data;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import project.soa.api.ICategoryController;
import project.soa.api.IDishController;
import project.soa.model.Category;
import project.soa.model.Dish;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ExpressionFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "menu")
@RequestScoped
@Data
public class MenuView {

    @EJB(lookup = "java:global/implementation/DishController")
    private IDishController dishController;

    @EJB(lookup = "java:global/implementation/CategoryController")
    private ICategoryController categoryController;

    private List<Dish> dishes;
    private List<Category> categories;

    private MenuModel categoryMenuModel;
    private int selectedCategoryId;
    private int counter;

    private String dishesMessage;

    private ExpressionFactory factory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();

    @PostConstruct
    private void init() {
        // categories
        categoryMenuModel = new DefaultMenuModel();
        categories = categoryController.getAllCategories();
        selectedCategoryId = -1;
        counter = 0;

        addCategoriesToTable();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            selectedCategoryId = Integer.parseInt(params.get("category"));
        }
        catch (Exception e) {
            selectedCategoryId = -1;
        }

        // dishes
        if (selectedCategoryId == -1) {
            dishes = dishController.getAllDishes();
            dishesMessage = "Wybierz kategorię z listy";
        }
        else {
            dishes = dishController.getDishesByCategory(categoryController.getCategory(selectedCategoryId));
            dishesMessage = "";
        }
    }

    private void addCategoriesToTable() {
        for (var category : categories) {
            if (category.getParent_id() == 0) {
                DefaultSubMenu item = PrepareSubMenu(category);
                categoryMenuModel.addElement(item);
            }
        }
    }

    private DefaultSubMenu PrepareSubMenu(Category category) {
        DefaultSubMenu item = new DefaultSubMenu(category.getName());
        // ustawienie przekierowania/wyboru menu

        int currentId = category.getId();
        for (var checkedCategory : categories) {
            int checkedParentId = checkedCategory.getParent_id();
            if (checkedParentId == currentId) {
                DefaultMenuItem childItem = new DefaultMenuItem(checkedCategory.getName());
                childItem.setUpdate("testText, menuTable");
                String url = String.format("menu.xhtml?category=%d", checkedCategory.getId());
                childItem.setUrl(url);
                item.addElement(childItem);
            }
        }
        return item;
    }
}
