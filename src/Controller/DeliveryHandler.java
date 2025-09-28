package Controller;

import java.util.ArrayList;

import Models.Delivery;
import Models.OrderHeader;
public class DeliveryHandler {

    public OrderHeader getOrderHeader(String idOrder) {
        return getOrderHeader(idOrder);
    }
    
    public ArrayList<OrderHeader> findAssignedDeliveries(String idUser) {
        // diagram 13 - view assigned deliveries
        return OrderHeader.findAssignedDeliveries(idUser);
    }

    public String processDelivery(String idOrder, String idUser) {
        // diagram 12 - assign order to courier
        return Delivery.processDelivery(idOrder, idUser);
    }
}
