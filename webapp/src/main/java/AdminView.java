import lombok.Data;
import project.soa.api.IAccountController;
import project.soa.api.IUserController;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "userView")
@ViewScoped
@Data
public class AdminView {
    @ManagedProperty(value = "#{userSession}")
    UserSession userSession;

    @EJB(lookup = "java:global/implementation/AccountController")
    IAccountController accountController;

    @EJB(lookup = "java:global/implementation/UserController")
    IUserController userController;

    List<User> users;

    public AdminView() {

    }

    private void refresh() {
        users = userController.getAllUsers();
    }
}
