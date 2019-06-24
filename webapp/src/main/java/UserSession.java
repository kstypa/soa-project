import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.soa.api.IAddressController;
import project.soa.model.Address;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;

@ManagedBean(name = "userSession")
@SessionScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private User user;

    ArrayList<Address> addresses;

    private String city;
    private String street;
    private String building;
    private int apartment;
    private String postal_code;

    @EJB(lookup = "java:global/implementation/AddressController")
    private IAddressController addressController;

    public ArrayList<Address> getAllAddresses(User user)
    {
        addresses=(ArrayList<Address>) addressController.getAddressesByUser(user);
        return addresses;
    }

    public void addAddress()
    {
        addressController.addAddress(city,street,building,apartment,postal_code,user);
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    public User.Role role() {
        return user.getRole();
    }

    public boolean isClient() {
        if (user == null)
            return false;
        return role() == User.Role.CLIENT;
    }

    public boolean isManager() {
        if (user == null)
            return false;
        return role() == User.Role.MANAGER;
    }

    public boolean isCook() {
        if (user == null)
            return false;
        return role() == User.Role.COOK;
    }

    public boolean isDeliveryBoy() {
        if (user == null)
            return false;
        return role() == User.Role.DELIVERY_BOY;
    }
}
