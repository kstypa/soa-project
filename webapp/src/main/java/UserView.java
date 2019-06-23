import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "userView")
@ViewScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
    @EJB
    OrderStorage orderStorage;

    public OrderStorage getOrderStorage() {
        return orderStorage;
    }

    public void setOrderStorage(OrderStorage storage){
        this.orderStorage=orderStorage;
    }
}
