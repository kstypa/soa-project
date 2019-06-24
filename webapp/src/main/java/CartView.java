import lombok.Data;
import project.soa.api.IOrderController;
import project.soa.api.ISubscriptionController;
import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@ManagedBean(name = "cart")
@ViewScoped
@Data
public class CartView {
    private ArrayList<Dish> dishes;

    private LocalDateTime deliveryDate;

    private Address address;


    @EJB(lookup = "java:global/implementation/SubscriptionController")
    ISubscriptionController subscriptionController;

    @EJB(lookup = "java:global/implementation/OrderController")
    IOrderController orderController;

    public void addDish(Dish dish)
    {
        if(dishes!=null)
        dishes.add(dish);
        else
        {
            dishes = new ArrayList<>();
            dishes.add(dish);
        }
    }

    public void subscribe(User user)
    {
        DayOfWeek dayOfWeek = deliveryDate.getDayOfWeek();
        LocalTime localTime = deliveryDate.toLocalTime();
        subscriptionController.addSubscription(user,address,localTime,dayOfWeek,this.dishes);
    }

    public void order(User user)
    {
        orderController.addOrder(user,address,deliveryDate,this.dishes);
    }

    public void refresh() { dishes=new ArrayList<>();}
}
