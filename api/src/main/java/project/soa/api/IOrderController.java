package project.soa.api;

import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.Order;
import project.soa.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderController {

    public List<Order> getAllOrders();

    public List<Order> getOrdersByUser(User user);

    public List<Order> getOrdersByAddress(Address address);

    public Order getOrder(int id);

    public List<Order> getAllPlacedOrders();

    public List<Order> getAllPreparingOrders();

    public List<Order> getAllPreparedOrders();

    public List<Order> getAllDeliveringOrders();

    public List<Order> getAllDeliveredOrders();

    public List<Order> getAllCanceledOrders();

    public Order addOrder(User user, Address address, LocalDateTime delivery_date, List<Dish> dishes);

    public Order editOrder(Order order, User user, Address address, LocalDateTime delivery_date, Order.Status status, List<Dish> dishes);

    public void cancelOrder(Order order);

    public void deleteOrder(Order order);
}
