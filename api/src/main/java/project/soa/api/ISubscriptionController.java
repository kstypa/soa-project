package project.soa.api;

import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.Subscription;
import project.soa.model.User;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ISubscriptionController {

    public List<Subscription> getAllSubscriptions();

    public List<Subscription> getSubscriptionsByUser(User user);

    public List<Subscription> getSubscriptionsByAddress(Address address);

    public Subscription getSubscription(int id);

    public Subscription addSubscription(User user, Address address, LocalTime delivery_hour, DayOfWeek delivery_day, List<Dish> dishes);

    public Subscription editSubscription(Subscription subscription, User user, Address address, LocalTime delivery_hour, DayOfWeek delivery_day, List<Dish> dishes);

    public void deleteSubscription(Subscription subscription);
}
