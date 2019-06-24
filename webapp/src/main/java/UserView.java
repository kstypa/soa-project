import lombok.Data;
import project.soa.api.IOrderController;
import project.soa.model.Order;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;

@ManagedBean(name = "userView")
@ViewScoped
@Data
public class UserView {
    OrderStorage orderStorage;

    @ManagedProperty(value = "#{userSession}")
    UserSession userSession;

    @EJB(lookup = "java:global/implementation/OrderController")
    IOrderController orderController;

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
                result.add("Your order status is now" + order.getStatus().toString());
            }
        }
        return result;
    }
}
