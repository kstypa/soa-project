package project.soa.controller;

import project.soa.api.ICategoryController;
import project.soa.model.Category;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote(ICategoryController.class)
public class CategoryController extends AbstractController implements ICategoryController {

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        Query query = entityManager.createQuery("from soa_categories", Category.class);

        try {
            categories = query.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category getCategory(int id) {
        return entityManager.find(Category.class, id);
    }
}
