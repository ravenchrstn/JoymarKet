package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import app.Connect;
import da.OrderHeaderDA;
import exception.NoRowsAffectedException;
import model.Delivery;
import model.OrderHeader;
import response.MultipleObjectsResponse;

public class CourierHandler {

	public MultipleObjectsResponse<OrderHeader> getAssignedDeliveries(String courierId) {
	    try {
	        ArrayList<OrderHeader> all = OrderHeader.getAssignedDeliveries(courierId);

	        // filtering out completed deliveries
	        ArrayList<OrderHeader> activeOnly = new ArrayList<>();
	        for (OrderHeader oh : all) {
	            if (!"completed".equalsIgnoreCase(oh.getStatus())) {
	                activeOnly.add(oh);
	            }
	        }

	        if (activeOnly.isEmpty()) {
	            return new MultipleObjectsResponse<>("No active deliveries assigned.", activeOnly);
	        }

	        return new MultipleObjectsResponse<>("", activeOnly);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return new MultipleObjectsResponse<>("Error fetching deliveries.", new ArrayList<>());
	    }
	}

    public String updateDeliveryStatus(String orderId, String courierId, String newStatus) {
        try {
            // update delivery table
            String q1 = "UPDATE delivery SET status = '" + newStatus +
                        "' WHERE idOrder = '" + orderId + "';";
            Connect.getInstance().execUpdate(q1);

            // if completed, also update orderheader
            if (newStatus.equalsIgnoreCase("completed")) {
                String q2 = "UPDATE orderheader SET status = 'completed' " +
                            "WHERE idOrder = '" + orderId + "';";
                Connect.getInstance().execUpdate(q2);
            }

            return "Status updated successfully!";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error updating status.";
        }
    }
    
    public MultipleObjectsResponse<OrderHeader> getCompletedDeliveries(String courierId) {
        try {
            ArrayList<OrderHeader> list =
                OrderHeaderDA.getOrderHeaderDA().findCompletedDeliveriesByCourier(courierId);

            if (list.isEmpty())
                return new MultipleObjectsResponse<>("No completed deliveries.", list);

            return new MultipleObjectsResponse<>("", list);

        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse<>("Error fetching completed deliveries.", new ArrayList<>());
        }
    }
}