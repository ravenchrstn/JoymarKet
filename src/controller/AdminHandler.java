package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import da.OrderHeaderDA;
import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import model.Courier;
import model.Delivery;
import model.OrderHeader;
import model.Product;
import response.MultipleObjectsResponse;

public class AdminHandler {

    // get unassigned order
	public MultipleObjectsResponse<OrderHeader> getUnassignedOrders() {
	    try {
	        ArrayList<OrderHeader> list = OrderHeaderDA.getOrderHeaderDA().findUnassignedOrders();

	        if (list.isEmpty())
	            return new MultipleObjectsResponse<>("No unassigned orders.", list);

	        return new MultipleObjectsResponse<>("", list);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return new MultipleObjectsResponse<>("Error fetching unassigned orders.", new ArrayList<>());
	    }
	}

    // get a list of all courier
    public MultipleObjectsResponse<Courier> getAllCouriers() {
        try {
            ArrayList<Courier> couriers = Courier.getAll();
            if (couriers.isEmpty()) {
                return new MultipleObjectsResponse<>("No couriers registered.", couriers);
            }
            return new MultipleObjectsResponse<>("", couriers);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse<>("An error occurred while fetching couriers.", new ArrayList<>());
        }
    }

    public String assignOrderToCourier(String idOrder, String idCourier) {

        if (idOrder == null || idOrder.isEmpty()) return "Please select an order.";
        if (idCourier == null || idCourier.isEmpty()) return "Please select a courier.";

        try {
            // validate if courier exists
            boolean exists = false;
            for (Courier c : Courier.getAll()) {
                if (c.getIdUser().equals(idCourier)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) return "Selected courier does not exist.";

            // insert to table
            Delivery.create(idOrder, idCourier, "pending");

            return "Courier assigned successfully!";

        } catch (NoRowsAffectedException e) {
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred while assigning courier.";
        }
    }

    // get all products for stock management
    public MultipleObjectsResponse<Product> getAllProducts() {
        try {
            ArrayList<Product> products = Product.getAllProducts();
            if (products.isEmpty()) {
                return new MultipleObjectsResponse<>("No products available.", products);
            }
            return new MultipleObjectsResponse<>("", products);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse<>("An error occurred while loading products.", new ArrayList<>());
        }
    }

    // update product stock (Admin Edit Product Stock)
    public String updateProductStock(String productId, int newStock) {

        if (productId == null) return "Invalid product.";

        if (newStock < 0) return "Stock cannot be negative.";

        try {
            Product.updateStock(productId, newStock);
            return "Stock updated successfully!";
        } 
        catch (NoRowsAffectedException e) {
            return e.getUserMessage();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred while updating stock.";
        }
    }
    
    public String cancelOrder(String orderId) {
        try {
            return OrderHeader.cancelOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return "Cancel failed. Try again.";
        }
    }
    
    // wrappers
    public MultipleObjectsResponse<OrderHeader> getAssignedOrders() {
        try {
            ArrayList<OrderHeader> list = OrderHeader.getAssignedOrders();
            return new MultipleObjectsResponse<>("", list);
        } catch (Exception e) {
            return new MultipleObjectsResponse<>("Error fetching assigned orders.", new ArrayList<>());
        }
    }
    
    public MultipleObjectsResponse<OrderHeader> getCompletedOrders() {
        try {
            return new MultipleObjectsResponse<>("", OrderHeader.getCompletedOrders());
        } catch (Exception e) {
            return new MultipleObjectsResponse<>("Error loading completed orders.", new ArrayList<>());
        }
    }
}
