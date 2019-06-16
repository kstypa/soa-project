package project.soa.controller;

import project.soa.api.IOrderController;
import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.Order;
import project.soa.model.User;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderController extends AbstractController implements IOrderController {
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
    public Order addOrder(User user, Address address, LocalDate delivery_date, List<Dish> dishes) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setDelivery_date(delivery_date);
        order.setDishes(dishes);

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
    public Order editOrder(Order order, User user, Address address, LocalDate delivery_date, Order.Status status, List<Dish> dishes) {
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
            entityManager.remove(order);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }
}
