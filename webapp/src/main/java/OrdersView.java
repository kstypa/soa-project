import lombok.Data;
import project.soa.api.IOrderController;
import project.soa.model.Order;
import project.soa.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "orders")
@ViewScoped
@Data
public class OrdersView {
    @EJB(lookup = "java:global/implementation/OrderController")
    IOrderController orderController;

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    List<Order> allOrders;

    List<Order> placedOrders;

    List<Order> preparingOrders;

    List<Order> preparedOrders;

    List<Order> deliveringOrders;

    List<Order> deliveredOrders;

    List<Order> canceledOrders;

    List<Order> placedOrPreparingOrders;

    List<Order> preparedOrDeliveringOrders;

    List<Order> ordersInTimeFrame;

    List<Order> ordersInTimeFrameForUser;

    @PostConstruct
    private void init(){
        allOrders=orderController.getAllOrders();
        placedOrders=orderController.getAllPlacedOrders();
        preparingOrders=orderController.getAllPreparingOrders();
        preparedOrders=orderController.getAllPreparedOrders();
        deliveringOrders=orderController.getAllDeliveringOrders();
        deliveredOrders=orderController.getAllDeliveredOrders();
        canceledOrders=orderController.getAllCanceledOrders();
        placedOrPreparingOrders=new ArrayList<>();
        preparedOrDeliveringOrders = new ArrayList<>();
        placedOrPreparingOrders.addAll(placedOrders);
        placedOrPreparingOrders.addAll(preparingOrders);
        preparedOrDeliveringOrders.addAll(preparedOrders);
        preparedOrDeliveringOrders.addAll(deliveringOrders);
    }

    public void getOrdersInTimeFrame(LocalDateTime date1, LocalDateTime date2)
    {
        ordersInTimeFrame=orderController.getOrdersBetweenDates(date1,date2);
    }

    public void getOrdersInTimeFrameForUser(LocalDateTime date1, LocalDateTime date2,User user)
    {
        getOrdersInTimeFrame(date1,date2);
        ordersInTimeFrameForUser=new ArrayList<>();
        for (Order order: ordersInTimeFrame) {
            if(order.getUser().equals(user))
            {
                ordersInTimeFrameForUser.add(order);
            }

        }
    }

    public Order.Status moveToNextStatus(Order.Status status)
    {
        switch (status) {
            case PLACED:
                return Order.Status.PREPARING;
            case PREPARING:
                return Order.Status.PREPARED;
            case PREPARED:
                return Order.Status.DELIVERING;
            case DELIVERING:
                return Order.Status.DELIVERED;
        }
        return Order.Status.PLACED;
    }

}
