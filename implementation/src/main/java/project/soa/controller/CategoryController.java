package project.soa.controller;

import project.soa.api.ICategoryController;
import project.soa.model.Category;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;
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
            System.out.println("select error");
        }

        return categories;
    }

    @Override
    public Category getCategory(int id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public Category getCategory(String name) {
        Query query = entityManager.createQuery("from soa_categories where name = :name", Category.class);
        query = query.setParameter("name", name);

        try {
            return (Category) query.getSingleResult();
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
    public Category addCategory(Category parent, String name) {
        Category category = new Category();
        category.setParent_id(parent.getId());
        category.setName(name);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(category);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error adding");
        }

        return category;
    }

    @Override
    public Category editCategory(Category category, Category parent, String name) {
        try {
            entityManager.getTransaction().begin();
            entityManager.detach(category);
            category.setParent_id(parent.getId());
            category.setName(name);
            entityManager.merge(category);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error editing");
        }

        return category;
    }

    @Override
    public void deleteCategory(Category category) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(category) ? category : entityManager.merge(category));
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }
}
