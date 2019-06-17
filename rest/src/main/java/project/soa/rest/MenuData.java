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
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;

@Path("dishes")
public class MenuData {
    @EJB(lookup = "java:global/implementation/DishController")
    private IDishController DishController;

    @GET
    @Path("/{categoryName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryAllDishesByCategoryName(@Context HttpServletRequest request, @PathParam("categoryName") String categoryName) {
        Locale requestLocale = request.getLocale();
        Category category = new Category();
        category.setName(categoryName);
        List<Dish> ret = DishController.getDishesByCategoryName(categoryName);
        System.out.println(requestLocale);
        ret = Translator.translateCourses(ret, requestLocale);
        return Response.ok(ret).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryAllDishes(@Context HttpServletRequest request) {
        Locale requestLocale = request.getLocale();
        List<Dish> ret = DishController.getAllNotArchivedAndApprovedDishes();
        System.out.println(requestLocale);
        ret = Translator.translateCourses(ret, requestLocale);
        return Response.ok(ret).build();
    }
}
