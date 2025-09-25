package Controller;

import java.util.HashMap;

import Models.Delivery;
import Models.OrderHeader;
import Validators.BusinessValidators;

public class DeliveryHandler {
    private BusinessValidators bv;

    public DeliveryHandler(BusinessValidators bv) {
        this.bv = bv;
    }

    public OrderHeader getOrderHeader(String idOrder) {
        return getOrderHeader(idOrder);
    }

    public HashMap<String, String> createDelivery(String idOrder, String idCourier) {
        HashMap<String, String> returnHashMap = new HashMap<String, String>();
        
        // validation
        if (this.bv.validateIdCourierExist(idCourier)) {
            returnHashMap.put("errorMessage", "idOrder is not found.");
            return returnHashMap;
        }

        if (this.bv.validateIdOrderExist(idOrder)) {
            returnHashMap.put("errorMessage", "idCourier is not found.");
            return returnHashMap;
        }
        
        Delivery delivery = Delivery.createDelivery(idOrder, idCourier);
        returnHashMap.put("idOrder", delivery.getIdOrder());
        returnHashMap.put("idCourier", delivery.getIdCourier());
        returnHashMap.put("status", delivery.getStatus());

        return returnHashMap;
    }
}
