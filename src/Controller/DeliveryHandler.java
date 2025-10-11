package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import Exceptions.NoRowsAffectedException;
import Models.Delivery;
import Models.OrderHeader;
import Responses.MultipleObjectsResponse;
public class DeliveryHandler {
    
    public MultipleObjectsResponse<OrderHeader> getAssignedDeliveries(String idUser) {
        // diagram 13 - view assigned deliveries
        try {
            ArrayList<OrderHeader> orderHeaders =  OrderHeader.getAssignedDeliveries(idUser);
            if (orderHeaders.isEmpty() == true) return new MultipleObjectsResponse<>("You don't have any active deliveries. Please wait until you are assigned one.", orderHeaders);
            MultipleObjectsResponse<OrderHeader> response = new MultipleObjectsResponse<OrderHeader>("", orderHeaders);
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse("An error occured while processing your request. Please try again later.", new ArrayList<>());
        }
    }

    public String processDelivery(String idOrder, String idUser) {
        // diagram 12 - assign order to courier
        String status = Delivery.getStatus(idOrder, idUser);
        try {
            if (status.equals("pending")) Delivery.updateStatus(idOrder, idUser, "in progress");
            else if (status.equals("in progress")) Delivery.updateStatus(idOrder, idUser, "delivered");
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your account is successfully created!";
    }
}
