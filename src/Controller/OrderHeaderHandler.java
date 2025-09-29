package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.InsufficientBalanceException;
import Exceptions.NoRowsAffectedException;
import Exceptions.OutOfStockException;
import Models.OrderHeader;
import Responses.MultipleHashMapsResponse;

public class OrderHeaderHandler {

    public MultipleHashMapsResponse findCustomerOrderHistories(String idUser) {
        // diagram 8 - view order history
        try {
            ArrayList<HashMap<String,Object>> customerOrderHistories = OrderHeader.getCustomerOrderHistories(idUser);
            if (customerOrderHistories.isEmpty() == true) return new MultipleHashMapsResponse("You have not made any orders. Place your first order now!", customerOrderHistories);
            MultipleHashMapsResponse response = new MultipleHashMapsResponse("", customerOrderHistories);
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleHashMapsResponse("An error occured while processing your request. Please try again later.", new ArrayList<>());
        }
    }

    public MultipleHashMapsResponse getAllOrders() {
        // diagram 11 - view all orders
        try {
            ArrayList<HashMap<String,Object>> orders = OrderHeader.getAllOrders();
            if (orders.isEmpty() == true) return new MultipleHashMapsResponse("Orders have not been made.", orders);
            return new MultipleHashMapsResponse("", orders);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleHashMapsResponse("An error occured while processing your request. Please try again later.", new ArrayList<>());
        }
    }

    public String checkoutOrder(String idUser, String promo) {
        // diagram 7 - checkout and place order
        try {
            OrderHeader.checkoutOrder(idUser, promo);
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (OutOfStockException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (InsufficientBalanceException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your checkout is successful!";
    }
}
