package project.soa.controller;

import project.soa.api.ISubscriptionController;
import project.soa.model.*;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionController extends AbstractController implements ISubscriptionController {
    @Override
    public List<Subscription> getAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<Subscription>();
        Query query = entityManager.createQuery("from soa_subscriptions", Subscription.class);

        try {
            subscriptions = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return subscriptions;
    }

    @Override
    public List<Subscription> getSubscriptionsByUser(User user) {
        List<Subscription> subscriptions = new ArrayList<Subscription>();
        Query query = entityManager.createQuery("from soa_subscriptions where user=:user", Subscription.class);
        query = query.setParameter("user", user);

        try {
            subscriptions = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return subscriptions;
    }

    @Override
    public List<Subscription> getSubscriptionsByAddress(Address address) {
        List<Subscription> subscriptions = new ArrayList<Subscription>();
        Query query = entityManager.createQuery("from soa_subscriptions where address=:address", Subscription.class);
        query = query.setParameter("address", address);

        try {
            subscriptions = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return subscriptions;
    }

    @Override
    public Subscription getSubscription(int id) {
        return entityManager.find(Subscription.class, id);
    }

    @Override
    public Subscription addSubscription(User user, Address address, LocalTime delivery_hour, DayOfWeek delivery_day, List<Dish> dishes) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setAddress(address);
        subscription.setDelivery_hour(delivery_hour);
        subscription.setDelivery_day(delivery_day);
        subscription.setDishes(dishes);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(subscription);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error adding");
        }

        return subscription;
    }

    @Override
    public Subscription editSubscription(Subscription subscription, User user, Address address, LocalTime delivery_hour, DayOfWeek delivery_day, List<Dish> dishes) {
        try {
            entityManager.getTransaction().begin();
            entityManager.detach(subscription);
            subscription.setUser(user);
            subscription.setAddress(address);
            subscription.setDelivery_hour(delivery_hour);
            subscription.setDelivery_day(delivery_day);
            subscription.setDishes(dishes);
            entityManager.merge(subscription);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error editing");
        }

        return subscription;
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(subscription) ? subscription : entityManager.merge(subscription));
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }
}
