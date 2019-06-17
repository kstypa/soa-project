package project.soa.api;

import project.soa.model.Category;
import project.soa.model.Dish;

import java.util.List;

public interface IDishController {

    public List<Dish> getAllDishes();

    public List<Dish> getDishesByCategory(Category category);

    public List<Dish> getDishesByCategoryName(String categoryName);

    public Dish getDish(int id);

    public Dish getDish(String name);

    public Dish addDish(String name, double price, Category category, String size, boolean approved);

    public Dish editDish(Dish dish, String name, double price, Category category, String size, boolean approved, boolean archived, boolean today, int times_ordered);

    public void setDishesApproved(List<Dish> dishes);

    public void setDishApproved(Dish dish);

    public void setDishesArchived(List<Dish> dishes);

    public void setDishArchived(Dish dish);

    public void setDishesToday(List<Dish> dishes);

    public Dish setDishToday(Dish dish);

    public void setDishNotToday(Dish dish);

    public void setDishNotArchived(Dish dish);

    public List<Dish> getTop10Dishes();

    public List<Dish> getAllArchivedAndApprovedDishes();

    public List<Dish> getArchivedAndApprovedDishesByCategory(Category category);

    public List<Dish> getAllNotArchivedAndApprovedDishes();

    public List<Dish> getNotArchivedAndApprovedDishesByCategory(Category category);

    public List<Dish> getAllTodayDishes();

    public void increaseTimesOrdered(Dish dish);

    public void deleteDish(Dish dish);
}
