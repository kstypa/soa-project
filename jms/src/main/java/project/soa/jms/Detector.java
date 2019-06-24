package project.soa.jms;

import project.soa.api.IOrderController;
import project.soa.model.Order;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public class Detector {

    @EJB(lookup = "java:global/implementation/OrderController")
    private IOrderController orderController;

    @EJB
    private Sender sender;

    private HashMap<Integer, Order> oldOrders;


    @Schedule(second = "*/40", minute = "*", hour = "*", persistent = false)
    public void check(){
        System.out.println("checking database");
        ArrayList<Order> newOrders=(ArrayList<Order>) orderController.getAllOrders();
        HashMap<Integer, Order> newOrdersMap= new HashMap<>();
        if(!oldOrders.isEmpty())
        {
            Order previousOrder;
            Order newOrder;
            for (Order order:newOrders) {
                newOrdersMap.put(order.getId(),order);
                newOrder=order;
                previousOrder=oldOrders.get(order.getId());
                if(previousOrder.getStatus()!=newOrder.getStatus())
                    sender.sendMessage(order.getUser().getId(),order.getId());
            }
        }
        else { for (Order order:newOrders) { newOrdersMap.put(order.getId(),order); } }
        oldOrders=newOrdersMap;
//        expiredSpots.forEach(element -> {
//            String message= Integer.toString((int) element.getSpotId());
//            sender.sendMessage(" bilet wygasl", ((int) element.getSpotId()),((int) element.getZoneId().getZoneId()));
//
//        });
//
//        unpaidSpots.forEach(element->{
//            String message= Integer.toString((int) element.getSpotId());
//            sender.sendMessage(" zajęto miejsce bez biletu", ((int) element.getSpotId()),((int) element.getZoneId().getZoneId()));
//        });
    }


}