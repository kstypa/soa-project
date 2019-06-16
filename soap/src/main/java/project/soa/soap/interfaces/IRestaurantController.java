package project.soa.soap.interfaces;

import project.soa.model.Dish;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IRestaurantController {

    @WebMethod
    public Dish addDishes(Dish newDish);
}
