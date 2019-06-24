import lombok.Data;
import project.soa.api.IAccountController;
import project.soa.api.IOrderController;
import project.soa.model.Order;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.HashSet;

@ManagedBean(name = "userView")
@ViewScoped
@Data
public class UserView {
    OrderStorage orderStorage;

    @ManagedProperty(value = "#{userSession}")
    UserSession userSession;

    @EJB(lookup = "java:global/implementation/OrderController")
    IOrderController orderController;

    @EJB(lookup = "java:global/implementation/AccountController")
    IAccountController accountController;

    private String oldPassword;
    private String newPassword;
    private String newPassword2;
    private String message;

    public UserView() {
        orderStorage = OrderStorage.getInstance();
    }

    public ArrayList<String> getAllOrderChanges()
    {
        ArrayList<String> result = new ArrayList<>();
        for (String IDs:orderStorage.arrayList) {
            String  userIdString = IDs.split("/")[0];
            String  orderIdString = IDs.split("/")[1];
            if(userIdString.equals(String.valueOf(userSession.getUser().getId())))
            {
                Order order = orderController.getOrder(Integer.valueOf(orderIdString));
                result.add("Status zamówienia nr " + order.getId() + ": " + order.getStatus().toString());
            }
        }
        // unikalne elementy
        return new ArrayList<>(new HashSet<>(result));
    }

    public void changePassword() {
        if (newPassword.equals(newPassword2)) {
            User ret = accountController.changePassword(userSession.getUser().getLogin(), oldPassword, newPassword);
            if (ret == null) {
                message = "Podaj poprawne stare hasło!";
            }
            else {
                message = "Zmiana hasła zakończona powodzeniem!";
            }
        }
        else {
            message = "Hasła muszą się zgadzać!";
        }
    }
}
