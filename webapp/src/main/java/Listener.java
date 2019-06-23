import netscape.javascript.JSObject;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName =
        "destinationType", propertyValue =
        "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination"
                ,propertyValue = "java:jboss/exported/jms/queue/SOA_test")})
public class Listener implements MessageListener {

//    @EJB(lookup = "java:global/webapp/OrderStorage")
    OrderStorage orderStorage;

    public Listener() {
        orderStorage = OrderStorage.getInstance();
    }

    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage)message;
        try {
            orderStorage.add(textMessage.getText());
            textMessage.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}