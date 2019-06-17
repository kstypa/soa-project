import lombok.Data;
import project.soa.api.IDishController;
import project.soa.model.Dish;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "index")
@ViewScoped
@Data
public class IndexView {
    @EJB(lookup = "java:global/implementation/DishController")
    IDishController dishController;

    List<Dish> topDishes;

    @PostConstruct
    private void init() {
        topDishes = dishController.getTop10Dishes();
    }
}
