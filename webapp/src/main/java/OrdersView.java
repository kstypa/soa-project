import lombok.Data;
import project.soa.api.IOrderController;
import project.soa.model.Order;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "orders")
@ViewScoped
@Data
public class OrdersView {
    @EJB(lookup = "java:global/implementation/OrdersController")
    IOrderController orderController;

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

    @PostConstruct
    private void init(){
        allOrders=orderController.getAllOrders();
        placedOrders=orderController.getAllPlacedOrders();
        preparingOrders=orderController.getAllPreparingOrders();
        preparedOrders=orderController.getAllPreparedOrders();
        deliveredOrders=orderController.getAllDeliveredOrders();
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
