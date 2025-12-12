package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.NoRowsAffectedException;
import model.Delivery;
import model.OrderHeader;
import response.MultipleObjectsResponse;
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
    
    public String updateDeliveryStatus(String idOrder, String idUser) {
        // diagram 12/14 - update delivery status by courier
        String status = Delivery.getStatus(idOrder, idUser);
        try {
            if ("pending".equals(status)) {
                Delivery.updateStatus(idOrder, idUser, "in progress");
            } else if ("in progress".equals(status)) {
                Delivery.updateStatus(idOrder, idUser, "delivered");
            }
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Delivery status is successfully updated!";
    }
}
