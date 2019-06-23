package project.soa.jms;

import project.soa.api.IOrderController;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.ArrayList;

@Singleton
public class Detector {

    @EJB(lookup = "java:global/implementation/OrderController")
    private IOrderController orderController;

    @EJB
    private Sender sender;



//    @Schedule(second = "*/40", minute = "*", hour = "*", persistent = false)
//    public void check(){
//        System.out.println("checking database");
//        ArrayList<Spot> expiredSpots=(ArrayList<Spot>) parkingManager.findExpiredSpots();
//        ArrayList<Spot> unpaidSpots=(ArrayList<Spot>) parkingManager.findUnpaidSpots();
//
//
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
//    }

    public void change(String change){
        String message = "You order has been changed to " + change;
        sender.sendMessage(message);
    }
}