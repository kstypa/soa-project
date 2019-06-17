package project.soa.controller;

import project.soa.api.IOrderController;
import project.soa.api.IDishController;
import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.Order;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderController extends AbstractController implements IOrderController {
    private DishController dishController = new DishController();

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_orders", Order.class);

        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        List<Order> orders = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_orders where user = :user", Order.class);
        query.setParameter("user", user);

        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByAddress(Address address) {
        List<Order> orders = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_orders where address = :address", Order.class);
        query.setParameter("address", address);

        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public Order getOrder(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> getAllPlacedOrders() {
        List<Order> orders = new ArrayList<>();
        Order.Status status = Order.Status.PLACED;
        Query query = entityManager.createQuery("from soa_orders where status =:status ", Order.class);
        query.setParameter("status", status);
        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }
    @Override
    public List<Order> getAllPreparingOrders() {
        List<Order> orders = new ArrayList<>();
        Order.Status status = Order.Status.PREPARING;
        Query query = entityManager.createQuery("from soa_orders where status =:status ", Order.class);
        query.setParameter("status", status);
        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }
    @Override
    public List<Order> getAllPreparedOrders() {
        List<Order> orders = new ArrayList<>();
        Order.Status status = Order.Status.PREPARED;
        Query query = entityManager.createQuery("from soa_orders where status =:status ", Order.class);
        query.setParameter("status", status);
        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public List<Order> getAllDeliveringOrders() {
        List<Order> orders = new ArrayList<>();
        Order.Status status = Order.Status.DELIVERING;
        Query query = entityManager.createQuery("from soa_orders where status =:status ", Order.class);
        query.setParameter("status", status);
        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public List<Order> getAllDeliveredOrders() {
        List<Order> orders = new ArrayList<>();
        Order.Status status = Order.Status.DELIVERED;
        Query query = entityManager.createQuery("from soa_orders where status =:status ", Order.class);
        query.setParameter("status", status);
        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public List<Order> getAllCanceledOrders() {
        List<Order> orders = new ArrayList<>();
        Order.Status status = Order.Status.CANCELED;
        Query query = entityManager.createQuery("from soa_orders where status =:status ", Order.class);
        query.setParameter("status", status);
        try {
            orders = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return orders;
    }

    @Override
    public Order addOrder(User user, Address address, LocalDateTime delivery_date, List<Dish> dishes) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setDelivery_date(delivery_date);
        order.setDishes(dishes);
        for(Dish dish: dishes)
        {
            dishController.increaseTimesOrdered(dish);
        }

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error adding");
        }

        return order;
    }

    @Override
    public Order editOrder(Order order, User user, Address address, LocalDateTime delivery_date, Order.Status status, List<Dish> dishes) {
        try {
            entityManager.getTransaction().begin();
            entityManager.detach(order);
            order.setUser(user);
            order.setAddress(address);
            order.setDelivery_date(delivery_date);
            order.setStatus(status);
            order.setDishes(dishes);
            entityManager.merge(order);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error editing");
        }

        return order;
    }

    @Override
    public void deleteOrder(Order order) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(order) ? order : entityManager.merge(order));
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }
    @Override
    public void cancelOrder(Order order) {
        editOrder(order,order.getUser(),order.getAddress(),order.getDelivery_date(),Order.Status.CANCELED,order.getDishes());
    }

}
