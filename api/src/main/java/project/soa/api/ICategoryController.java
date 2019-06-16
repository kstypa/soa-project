package project.soa.api;

import project.soa.model.Category;

import java.util.List;

public interface ICategoryController {

    public List<Category> getAllCategories();

    public Category getCategory(int id);

    public Category getCategory(String name);

    public Category addCategory(Category parent, String name);

    public Category editCategory(Category category, Category parent, String name);

    public void deleteCategory(Category category);
}
