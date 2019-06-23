import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "userView")
@ViewScoped
@Data
public class UserView {
    OrderStorage orderStorage;

    public UserView() {
        orderStorage = OrderStorage.getInstance();
    }
}
