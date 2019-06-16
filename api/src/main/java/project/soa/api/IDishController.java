package project.soa.api;

import project.soa.model.Category;
import project.soa.model.Dish;

import java.util.List;

public interface IDishController {

    public List<Dish> getAllDishes();

    public List<Dish> getDishesByCategory(Category category);

    public Dish getDish(int id);

    public Dish getDish(String name);

    public Dish addDish(String name, double price, Category category, String size, boolean approved);

    public Dish editDish(Dish dish, String name, double price, Category category, String size, boolean approved, boolean archived, boolean today, int times_ordered);

    public List<Dish> setDishesApproved(List<Dish> dishes);

    public Dish setDishApproved(Dish dish);

    public List<Dish> setDishesArchived(List<Dish> dishes);

    public Dish setDishArchived(Dish dish);

    public List<Dish> setDishesToday(List<Dish> dishes);

    public Dish setDishToday(Dish dish);

    public void deleteDish(Dish dish);
}
