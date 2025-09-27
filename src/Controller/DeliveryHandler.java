package Controller;

import java.util.ArrayList;

import Models.Delivery;
import Models.OrderHeader;
public class DeliveryHandler {

    public OrderHeader getOrderHeader(String idOrder) {
        return getOrderHeader(idOrder);
    }
    
    public ArrayList<OrderHeader> getAssignedDeliveries(String idCourier) {
        // diagram 13 - view assigned deliveries

        return OrderHeader.findAssignedDeliveries(idCourier);
    }

    public String processDelivery(String idOrder, String idCourier) {
        // diagram 12 - assign order to courier
    
        return Delivery.updateStatus(idOrder, idCourier);
    }

    // commit message: fix view assign order to courier feature, delete unnecessary business validators and insert them into models, and change wrong file validation location, clean code, and fix wrong return in data access, fix view order histories feature.
}
