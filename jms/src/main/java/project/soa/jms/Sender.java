package project.soa.jms;

import project.soa.model.Order;
import project.soa.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.Date;

@Stateless
public class Sender {

    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Resource(mappedName = "java:jboss/exported/jms/queue/SOA_test")
    private Queue queue;


    public void sendMessage(int userId, int orderId) {
        try {
            send(String.valueOf(userId)+"/"+String.valueOf(orderId));
        }catch (Exception e){
            System.out.println("Wystąpił błąd przy wysyłaniu: "+e);
        }
    }

    private void send(String Message) {
        TextMessage textMessage = context.createTextMessage(Message);
        context.createProducer().send(queue, textMessage);
    }

}
