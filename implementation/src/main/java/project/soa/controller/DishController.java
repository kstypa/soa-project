package project.soa.controller;

import clojure.lang.IFn;
import project.soa.api.IDishController;
import project.soa.model.Category;
import project.soa.model.Dish;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote(IDishController.class)
public class DishController extends AbstractController implements IDishController {

    @Override
    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes", Dish.class);

        try {
            dishes = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return dishes;
    }

    @Override
    public List<Dish> getDishesByCategory(Category category) {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where category = :category", Dish.class);
        query = query.setParameter("category", category);

        try {
            dishes = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return dishes;
    }

    @Override
    public Dish getDish(int id) {
        return entityManager.find(Dish.class, id);
    }

    @Override
    public Dish getDish(String name) {
        Query query = entityManager.createQuery("from soa_dishes where name = :name", Dish.class);
        query = query.setParameter("name", name);

        try {
            return (Dish) query.getSingleResult();
        }
        catch (NonUniqueResultException e) {
            System.out.println("non unique " + name);
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Dish addDish(String name, double price, Category category, String size, boolean approved) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setPrice(price);
        dish.setCategory(category);
        dish.setSize(size);
        dish.setApproved(approved);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(dish);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error adding");
        }

        return dish;
    }

    @Override
    public Dish editDish(Dish dish, String name, double price, Category category, String size, boolean approved, boolean archived, boolean today, int times_ordered) {
        try {
            entityManager.getTransaction().begin();
            entityManager.detach(dish);
            dish.setName(name);
            dish.setPrice(price);
            dish.setCategory(category);
            dish.setSize(size);
            dish.setApproved(approved);
            dish.setArchived(archived);
            dish.setToday(today);
            dish.setTimes_ordered(times_ordered);
            entityManager.merge(dish);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error editing");
        }

        return dish;
    }

    @Override
    public void deleteDish(Dish dish) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(dish);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }

    @Override
    public List<Dish> setDishesApproved(List<Dish> dishes)
    {
        for (Dish dish: dishes)
            setDishApproved(dish);
        return dishes;
    }

    @Override
    public Dish setDishApproved(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),true,dish.isArchived(),dish.isToday(),dish.getTimes_ordered());
        return dish;
    }

    @Override
    public List<Dish> setDishesArchived(List<Dish> dishes)
    {
        for (Dish dish: dishes)
            setDishArchived(dish);
        return dishes;
    }

    @Override
    public Dish setDishArchived(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),true,dish.isToday(),dish.getTimes_ordered());
        return dish;
    }

    @Override
    public List<Dish> setDishesToday(List<Dish> dishes)
    {
        for (Dish dish: dishes)
            setDishToday(dish);
        return dishes;
    }

    @Override
    public Dish setDishToday(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),dish.isArchived(),true,dish.getTimes_ordered());
        return dish;
    }

}

