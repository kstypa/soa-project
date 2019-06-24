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
                ,propertyValue = "java:jboss/exported/jms/queue/SOA_Test")})
public class Listener implements MessageListener {

    OrderStorage orderStorage;

    public Listener() {
        orderStorage = OrderStorage.getInstance();
    }

    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage)message;
        try {
            String messageText=textMessage.getText();
            System.out.println(messageText);
            orderStorage.add(messageText);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}