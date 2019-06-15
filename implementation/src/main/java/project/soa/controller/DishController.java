package project.soa.controller;

import project.soa.api.IDishController;
import project.soa.model.Dish;

import javax.ejb.Remote;
import javax.ejb.Stateless;
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
            e.printStackTrace();
        }

        return dishes;
    }

    @Override
    public Dish getDish(int id) {
        return entityManager.find(Dish.class, id);
    }
}
