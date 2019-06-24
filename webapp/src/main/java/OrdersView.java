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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    List<Order> ordersInTimeFrameList;

    List<Order> ordersInTimeFrameForUserList;

    Date userDate1;
    Date userDate2;

    @PostConstruct
    private void init(){
        placedOrPreparingOrders = new ArrayList<>();
        preparedOrDeliveringOrders = new ArrayList<>();
        refresh();
    }

    private void refresh() {
        allOrders = orderController.getAllOrders();
        placedOrders = orderController.getAllPlacedOrders();
        preparingOrders = orderController.getAllPreparingOrders();
        preparedOrders = orderController.getAllPreparedOrders();
        deliveringOrders = orderController.getAllDeliveringOrders();
        deliveredOrders = orderController.getAllDeliveredOrders();
        canceledOrders = orderController.getAllCanceledOrders();

        placedOrPreparingOrders.clear();
        placedOrPreparingOrders.addAll(placedOrders);
        placedOrPreparingOrders.addAll(preparingOrders);

        preparedOrDeliveringOrders.clear();
        preparedOrDeliveringOrders.addAll(preparedOrders);
        preparedOrDeliveringOrders.addAll(deliveringOrders);
    }

    public void getOrdersInTimeFrame(LocalDateTime date1, LocalDateTime date2)
    {
        ordersInTimeFrameList =orderController.getOrdersBetweenDates(date1,date2);
    }

    public void getOrdersInTimeFrameForUser(User user)
    {
        LocalDateTime date1 = convertToLocalDateTimeViaInstant(userDate1);
        LocalDateTime date2 = convertToLocalDateTimeViaInstant(userDate2);

        getOrdersInTimeFrame(date1, date2);
        ordersInTimeFrameForUserList = new ArrayList<>();
        for (Order order: ordersInTimeFrameList) {
            if(order.getUser().equals(user))
            {
                ordersInTimeFrameForUserList.add(order);
            }

        }
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public void moveToNextStatus(Order order)
    {
        Order.Status newStatus;
        switch (order.getStatus()) {
            case PLACED:
                newStatus = Order.Status.PREPARING; break;
            case PREPARING:
                newStatus = Order.Status.PREPARED; break;
            case PREPARED:
                newStatus = Order.Status.DELIVERING; break;
            case DELIVERING:
                newStatus = Order.Status.DELIVERED; break;
            default:
                newStatus = Order.Status.PLACED;
        }

        orderController.editOrder(order, order.getUser(), order.getAddress(), order.getDelivery_date(), newStatus, order.getDishes());
        refresh();
    }

    public void resetStatusToPlaced(Order order) {
        orderController.editOrder(order, order.getUser(), order.getAddress(), order.getDelivery_date(), Order.Status.PLACED, order.getDishes());
        refresh();
    }

    public void cancelOrder(Order order) {
        orderController.editOrder(order, order.getUser(), order.getAddress(), order.getDelivery_date(), Order.Status.CANCELED, order.getDishes());
        getOrdersInTimeFrameForUser(order.getUser());
    }

}
