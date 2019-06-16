package project.soa.soap.implementation;

import project.soa.model.Dish;
import project.soa.api.IDishController;
import project.soa.soap.interfaces.IRestaurantController;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "project.soa.soap.interfaces.IRestaurantController")
public class RestaurantController implements IRestaurantController {
    @EJB(lookup = "java:global/implementation/DishController")
    private IDishController dishController;

    @Override
    @WebMethod
    public Dish addDishes(Dish newDish)
    {return dishController.addDish(newDish.getName(),newDish.getPrice(),newDish.getCategory(),newDish.getSize(),false);}
}
