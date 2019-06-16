package project.soa.controller;

import project.soa.api.ISubscriptionController;
import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.Subscription;
import project.soa.model.User;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class SubscriptionController extends AbstractController implements ISubscriptionController {
    @Override
    public List<Subscription> getAllSubscriptions() {
        return null;
    }

    @Override
    public List<Subscription> getSubscriptionsByUser(User user) {
        return null;
    }

    @Override
    public List<Subscription> getSubscriptionsByAddress(Address address) {
        return null;
    }

    @Override
    public Subscription getSubscription(int id) {
        return null;
    }

    @Override
    public Subscription addSubscription(User user, Address address, LocalTime delivery_hour, DayOfWeek delivery_day, List<Dish> dishes) {
        return null;
    }

    @Override
    public Subscription editSubscription(Subscription subscription, User user, Address address, LocalTime delivery_hour, DayOfWeek delivery_day, List<Dish> dishes) {
        return null;
    }

    @Override
    public void deleteSubscription(Subscription subscription) {

    }
}
