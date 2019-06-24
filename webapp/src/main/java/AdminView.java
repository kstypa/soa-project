import lombok.Data;
import project.soa.api.IAccountController;
import project.soa.api.IUserController;
import project.soa.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name = "adminView")
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
    List<User.Role> roles;

    int editId;
    String editLogin;
    String editRoleName;

    @PostConstruct
    private void init() {
        roles = Arrays.asList(User.Role.values());
        refresh();
    }

    private void refresh() {
        users = userController.getAllUsers();
    }

    public void startEdit(int userId, String login, User.Role role) {
        editId = userId;
        editLogin = login;
        editRoleName = role.name();
    }

    public void editRole() {
        User user = userController.getUser(editId);
        userController.editUserRole(user, User.Role.valueOf(editRoleName));

        refresh();
    }
}
