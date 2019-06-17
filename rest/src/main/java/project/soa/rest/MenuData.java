package project.soa.rest;

import project.soa.api.IDishController;
import project.soa.api.ICategoryController;
import project.soa.model.Category;
import project.soa.model.Dish;

import javax.servlet.http.HttpServletRequest;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Locale;

@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MenuData {
    @EJB(lookup = "java:global/core/CourseService")
    private IDishController DishController;

    @GET
    @Path("/category/{categoryName}")
    @Produces("application/json")
    public List<Dish> queryAllDishesByCategoryName(@Context HttpServletRequest request, @PathParam("categoryName") String categoryName) {
        Locale requestLocale = request.getLocale();
        Category category = new Category();
        category.setName(categoryName);
        List<Dish> ret = DishController.getDishesByCategoryName(categoryName);
        System.out.println(requestLocale);
        ret = Translator.translateCourses(ret, requestLocale);
        return ret;
    }

    @GET
    @Path("/category/")
    public List<Dish> queryAllCategories(@Context HttpServletRequest request) {
        Locale requestLocale = request.getLocale();
        List<Dish> ret = DishController.getAllNotArchivedAndApprovedDishes();
        System.out.println(requestLocale);
        ret = Translator.translateCourses(ret, requestLocale);
        return ret;
    }
}
