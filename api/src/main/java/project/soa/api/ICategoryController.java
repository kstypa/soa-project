package project.soa.api;

import project.soa.model.Category;

import java.util.List;

public interface ICategoryController {
    public List<Category> getAllCategories();
    public Category getCategory(int id);
}
