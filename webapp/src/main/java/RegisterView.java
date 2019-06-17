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

@ManagedBean(name = "registerView")
@ViewScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterView {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String message;

    @EJB(lookup = "java:global/implementation/AccountController")
    private IAccountController accountController;

    public void register() {
        User user = accountController.addUser(login, password, firstName, lastName);
        if (user != null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                System.out.println("error redirecting");
            }
        }
        else {
            message = "Nazwa użytkownika jest zajęta!";
        }
    }
}
