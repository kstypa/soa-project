import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.soa.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userSession")
@SessionScoped
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    private User user;

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
