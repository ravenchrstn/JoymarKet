package Controller;

import Models.Courier;
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

    public String processDelivery(String idOrder, String idCourier) {
        // diagram 12 - assign order to courier
        Courier oh = Courier.getCourier(idCourier);
        
        // validation
        if (this.bv.doesCourierExist(oh) == false) {
            return "courier does not exist.";
        } else if (this.bv.isCourier(oh) == false) {
            return "Not a courier.";
        }

        if (this.bv.doesOrderExist(idOrder) == false) {
            return "order does not exist.";
        }
        
        String status = Delivery.updateStatus(idOrder, idCourier, "in progress");
        return status;
    }
}
