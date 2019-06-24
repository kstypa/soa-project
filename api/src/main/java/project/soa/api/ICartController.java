package project.soa.api;

import project.soa.model.Address;
import project.soa.model.Dish;
import project.soa.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ICartController {
    void addDish(Dish dish);

    void order(User user, int addressId, LocalDateTime deliveryDate);

    void subscribe(User user, int addressId, LocalDateTime deliveryDate);

    List<Dish> getDishes();
}
