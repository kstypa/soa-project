package project.soa.controller;

import project.soa.api.IAddressController;
import project.soa.api.ICartController;
import project.soa.api.IOrderController;
import project.soa.api.ISubscriptionController;
import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Stateful
@StatefulTimeout(value = 20)
@Remote(ICartController.class)
public class CartController extends AbstractController implements ICartController {

    @EJB(lookup = "java:global/implementation/OrderController")
    IOrderController orderController;

    @EJB(lookup = "java:global/implementation/SubscriptionController")
    ISubscriptionController subscriptionController;

    @EJB(lookup = "java:global/implementation/AddressController")
    IAddressController addressController;

    private List<Dish> dishes;

    @PostConstruct
    private void init() {
        dishes = new ArrayList<>();
        System.out.println("cart init");
    }

    @Override
    public void addDish(Dish dish) {
        dishes.add(dish);
        System.out.println("cart add " + dish.getName());
    }

    @Override
    public void order(User user, int addressId, LocalDateTime deliveryDate) {
        Address address = addressController.getAddress(addressId);
        orderController.addOrder(user, address, deliveryDate, this.dishes);
    }

    @Override
    public void subscribe(User user, int addressId, LocalDateTime deliveryDate) {
        Address address = addressController.getAddress(addressId);
        DayOfWeek dayOfWeek = deliveryDate.getDayOfWeek();
        LocalTime localTime = deliveryDate.toLocalTime();
        subscriptionController.addSubscription(user, address, localTime, dayOfWeek, this.dishes);
    }

    @Override
    public List<Dish> getDishes() {
        System.out.println(dishes);
        return dishes;
    }
}
