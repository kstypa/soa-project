import lombok.Data;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import project.soa.api.IAddressController;
import project.soa.api.ICartController;
import project.soa.api.ICategoryController;
import project.soa.api.IDishController;
import project.soa.model.Address;
import project.soa.model.Category;
import project.soa.model.Dish;
import project.soa.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "menu")
@ViewScoped
@Data
public class MenuView {

    @EJB(lookup = "java:global/implementation/DishController")
    private IDishController dishController;

    @EJB(lookup = "java:global/implementation/CategoryController")
    private ICategoryController categoryController;

    @EJB(lookup = "java:global/implementation/CartController")
    private ICartController cartController;

    @EJB(lookup = "java:global/implementation/AddressController")
    private IAddressController addressController;

    @ManagedProperty(value = "#{userSession}")
    private UserSession userSession;

    private List<Dish> dishes;
    private List<Dish> archivedDishes;
    private List<Category> categories;
    private List<Category> editCategories;
    private List<Dish> selectedDishes;
    private List<Dish> selectedArchivedDishes;

    private MenuModel categoryMenuModel;
    private int selectedCategoryId;
    private int counter;

    private String dishesMessage;

    private String newName;
    private Category newCategory;
    private double newPrice;
    private String newSize;

    private int editId;
    private String editName;
    private Category editCategory;
    private double editPrice;
    private String editSize;

    // cart
    private ArrayList<Address> addresses;
    private int selectedAddressId;
    private List<Dish> orderDishes;
    private Date deliveryDate;

    @PostConstruct
    private void init() {
        // categories
        categoryMenuModel = new DefaultMenuModel();
        categories = categoryController.getAllCategories();
        editCategories = new ArrayList<>();
        selectedCategoryId = -1;
        counter = 0;

        addCategoriesToTable();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            selectedCategoryId = Integer.parseInt(params.get("category"));
        }
        catch (Exception e) {
            selectedCategoryId = -1;
        }

        // dishes
        refreshDishes();
    }

    private void addCategoriesToTable() {
        for (var category : categories) {
            if (category.getParent_id() == 0) {
                DefaultSubMenu item = PrepareSubMenu(category);
                categoryMenuModel.addElement(item);
            }
            else {
                editCategories.add(category);
            }
        }
    }

    private DefaultSubMenu PrepareSubMenu(Category category) {
        DefaultSubMenu item = new DefaultSubMenu(category.getName());
        // ustawienie przekierowania/wyboru menu

        int currentId = category.getId();
        for (var checkedCategory : categories) {
            int checkedParentId = checkedCategory.getParent_id();
            if (checkedParentId == currentId) {
                DefaultMenuItem childItem = new DefaultMenuItem(checkedCategory.getName());
                childItem.setUpdate("testText, menuTable");
                String url = String.format("menu.xhtml?category=%d", checkedCategory.getId());
                childItem.setUrl(url);
                item.addElement(childItem);
            }
        }
        return item;
    }

    private void refreshDishes() {
        if (selectedCategoryId == -1) {
            dishes = dishController.getAllNotArchivedAndApprovedDishes();
            dishesMessage = "Wybierz kategorię z listy";
        }
        else {
            dishes = dishController.getNotArchivedAndApprovedDishesByCategory(categoryController.getCategory(selectedCategoryId));
            dishesMessage = "";
        }

        archivedDishes = dishController.getAllArchivedAndApprovedDishes();
    }

    public void addDish() {
        dishController.addDish(newName, newPrice, newCategory, newSize, true);
        refreshDishes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Dodano pozycję " + newName));
    }

    public void deleteSelectedDishes() {
        for (var dish : selectedDishes) {
            dishController.deleteDish(dish);
        }
        refreshDishes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Usunięto wybrane pozycje"));
    }

    public void deleteSelectedArchivedDishes() {
        for (var dish : selectedArchivedDishes) {
            dishController.deleteDish(dish);
        }
        refreshDishes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Usunięto wybrane pozycje"));
    }

    public void startEdit(int id, String name, Category category, double price, String size) {
        editId = id;
        editName = name;
        editCategory = category;
        editPrice = price;
        editSize = size;
    }

    public void editDish() {
        Dish edited = new Dish();
        for (var dish : dishes) {
            if (dish.getId() == editId) {
                edited = dish;
                break;
            }
        }
        dishController.editDish(edited, editName, editPrice, editCategory, editSize, true, edited.isArchived(), edited.isToday(), edited.getTimes_ordered());
        refreshDishes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Edytowano pozycję " + editName));
    }

    public void archiveDish(Dish dish) {
        dishController.setDishArchived(dish);
        refreshDishes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Zarchiwizowano pozycję " + dish.getName()));
    }

    public void dearchiveDish(Dish dish) {
        dishController.setDishNotArchived(dish);
        refreshDishes();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Zdearchiwizowano pozycję " + dish.getName()));
    }

    public void addToCart(Dish dish) {
        cartController.addDish(dish);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sukces", "Dodano pozycję " + dish.getName() + " do koszyka"));
    }

    public void subscribeCart()
    {
        LocalDateTime deliveryDateTime = convertToLocalDateTimeViaInstant(deliveryDate);
        cartController.subscribe(userSession.getUser(), selectedAddressId, deliveryDateTime);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Zasubskrybowano"));
    }

    public void orderCart()
    {
        LocalDateTime deliveryDateTime = convertToLocalDateTimeViaInstant(deliveryDate);
        cartController.order(userSession.getUser(), selectedAddressId, deliveryDateTime);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Zamówienie złożone"));
    }

    public void refreshCart() {
        addresses = getAllAddresses(userSession.getUser());
        orderDishes = cartController.getDishes();
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public ArrayList<Address> getAllAddresses(User user) {
        addresses = (ArrayList<Address>) addressController.getAddressesByUser(user);
        return addresses;
    }
}
