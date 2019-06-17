import lombok.Data;
import project.soa.api.IDishController;
import project.soa.model.Dish;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@ManagedBean(name = "index")
@ViewScoped
@Data
public class IndexView {
    @EJB(lookup = "java:global/implementation/DishController")
    IDishController dishController;

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    List<Dish> topDishes;
    private String message;

    @PostConstruct
    private void init() {
        topDishes = dishController.getTop10Dishes();

        if (userSession.isLoggedIn()) {
            message = "Witaj " + userSession.getUser().getLogin() + "!";
        }
        else {
            message = "Zaloguj się aby korzystać z serwisu!";
        }
    }

    public void logout() {
        if (userSession.isLoggedIn()) {
            userSession.logout();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                System.out.println("error redirecting");
            }
        }
    }
}
