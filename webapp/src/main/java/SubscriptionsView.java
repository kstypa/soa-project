import lombok.Data;
import project.soa.api.IOrderController;
import project.soa.api.ISubscriptionController;
import project.soa.model.Subscription;
import project.soa.model.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;


@ManagedBean(name = "subscriptions")
@ViewScoped
@Data
public class SubscriptionsView {
    @EJB(lookup = "java:global/implementation/SubscriptionController")
    ISubscriptionController subscriptionController;

    public ArrayList<Subscription> getAllSubscriptionsForUser(User user)
    {
        return (ArrayList<Subscription>)subscriptionController.getSubscriptionsByUser(user);
    }

    public void cancelSubscription(Subscription subscription)
    {
        subscriptionController.deleteSubscription(subscription);
    }
}
