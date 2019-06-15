package project.soa.api;

import project.soa.model.Dish;

import java.util.List;

public interface IDishController {
    public List<Dish> getAllDishes();
    public Dish getDish(int id);
}
