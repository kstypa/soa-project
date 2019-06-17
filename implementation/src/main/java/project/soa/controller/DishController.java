package project.soa.controller;

import project.soa.api.IDishController;
import project.soa.model.Category;
import project.soa.model.Dish;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.*;

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
        Query query = entityManager.createQuery("from soa_dishes where category = :category and approved = true", Dish.class);
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
    public List<Dish> getDishesByCategoryName(String categoryName)
    {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where category.name = :categoryName and approved = true", Dish.class);
        query = query.setParameter("categoryName", categoryName);

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
    public void setDishesApproved(List<Dish> dishes)
    {
        for (Dish dish: dishes)
            setDishApproved(dish);

    }

    @Override
    public void setDishApproved(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),true,dish.isArchived(),dish.isToday(),dish.getTimes_ordered());

    }

    @Override
    public void setDishesArchived(List<Dish> dishes)
    {
        for (Dish dish: dishes)
            setDishArchived(dish);
    }

    @Override
    public void setDishArchived(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),true,dish.isToday(),dish.getTimes_ordered());
    }

    @Override
    public void setDishesToday(List<Dish> dishes)
    {
        for (Dish dish: dishes)
            setDishToday(dish);
    }

    @Override
    public Dish setDishToday(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),dish.isArchived(),true,dish.getTimes_ordered());
        return dish;
    }

    @Override
    public void setDishNotToday(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),dish.isArchived(),false,dish.getTimes_ordered());
    }

    @Override
    public void setDishNotArchived(Dish dish)
    {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),false,dish.isToday(),dish.getTimes_ordered());
    }

    @Override
    public List<Dish> getTop10Dishes() {
        ArrayList<Dish> allDishes= new ArrayList(getAllNotArchivedAndApprovedDishes());
        Collections.sort(allDishes,new DishComparator());
        ArrayList<Dish> result = new ArrayList<Dish>();
        for (int i=0;i<10;i++)
        {
            result.add(allDishes.get(i));
        }
        return result;
    }

    @Override
    public List<Dish> getAllArchivedAndApprovedDishes()
    {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where archived = true and approved = true", Dish.class);


        try {
            dishes = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return dishes;
    }

    @Override
    public List<Dish> getArchivedAndApprovedDishesByCategory(Category category) {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where category = :category and archived = true and approved = true", Dish.class);
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
    public List<Dish> getAllNotArchivedAndApprovedDishes()
    {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where archived = false and approved = true", Dish.class);


        try {
            dishes = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return dishes;
    }

    @Override
    public List<Dish> getNotArchivedAndApprovedDishesByCategory(Category category) {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where category = :category and archived = false and approved = true", Dish.class);
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
    public List<Dish> getAllTodayDishes()
    {
        List<Dish> dishes = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_dishes where today = true and archived = false and approved = true", Dish.class);


        try {
            dishes = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return dishes;
    }

    @Override
    public void increaseTimesOrdered(Dish dish) {
        editDish(dish,dish.getName(),dish.getPrice(),dish.getCategory(),dish.getSize(),dish.isApproved(),dish.isArchived(),dish.isToday(),dish.getTimes_ordered()+1);
    }

    @Override
    public void deleteDish(Dish dish) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(dish) ? dish : entityManager.merge(dish));
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
            e.printStackTrace();
        }
    }

    class DishComparator implements Comparator<Dish>
    {
        @Override
        public int compare(Dish o1, Dish o2) {
            if (o1.getTimes_ordered() < o2.getTimes_ordered()){
                return 1;
            }
            else if (o1.getTimes_ordered() == o2.getTimes_ordered()) {
                return 0;
            }
            else {
                return -1;
            }
        }
    }
}

