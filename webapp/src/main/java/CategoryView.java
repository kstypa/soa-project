import lombok.Data;
import project.soa.api.ICategoryController;
import project.soa.model.Category;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "Category")
@Data
@ViewScoped
public class CategoryView implements Serializable {

    @EJB(lookup = "java:global/implementation/CategoryController")
    private ICategoryController categoryController;

    private List<Category> categories;

    @PostConstruct
    private void init() {
        categories = categoryController.getAllCategories();
    }
}
