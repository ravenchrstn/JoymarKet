package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.InvalidInputException;
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
    
    // update delivery by courier (diagram 14)
    public String updateDeliveryStatus(String idOrder, String idUser, String newStatus) {
        try {
            if (idOrder == null || idOrder.isEmpty()) 
                throw new InvalidInputException("Order id is empty.", "Select an order.");

            if (idUser == null || idUser.isEmpty()) 
                throw new InvalidInputException("User id is empty.", "Courier not valid.");

            if (newStatus == null || newStatus.isEmpty()) 
                throw new InvalidInputException("Status is empty.", "Choose a status.");

            // only allowed statuses
            String s = newStatus.toLowerCase();
            if (!s.equals("pending") && !s.equals("in progress") && !s.equals("delivered"))
                throw new InvalidInputException("Invalid status.", "Status must be Pending / In Progress / Delivered.");

            Delivery.updateStatus(idOrder, idUser, s);
            return "Delivery status updated.";

        } catch (InvalidInputException | SQLException | NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
