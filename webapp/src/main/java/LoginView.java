import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.soa.api.IAccountController;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name = "loginView")
@ViewScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginView {
    private String login;
    private String password;
    private String message;

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    @EJB(lookup = "java:global/implementation/AccountController")
    private IAccountController accountController;

    public void login() {
        User user = accountController.loginUser(login, password);
        if (user != null) {
            userSession.setUser(user);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                System.out.println("error redirecting");
            }
        }
        else {
            message = "Złe dane logowania!";
        }
    }
}
