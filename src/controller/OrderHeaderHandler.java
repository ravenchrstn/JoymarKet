package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import exception.NoRowsAffectedException;
import model.CartItem;
import model.Customer;
import model.OrderDetail;
import model.OrderHeader;
import model.Promo;
import response.MultipleHashMapsResponse;

public class OrderHeaderHandler {

    public MultipleHashMapsResponse getCustomerOrderHistories(String idUser) {
        // diagram 8 - view order history
        try {
            ArrayList<HashMap<String,Object>> customerOrderHistories = OrderHeader.getCustomerOrderHistories(idUser);
            if (customerOrderHistories.isEmpty() == true) return new MultipleHashMapsResponse("You have not made any orders. Place your first order now!", customerOrderHistories);
            return new MultipleHashMapsResponse("", customerOrderHistories);
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

    public String checkoutOrder(String idUser, String promoCode) {
        // diagram 7 - checkout and place order
        try {
            ArrayList<HashMap<String, Object>> cartItemsAndStock = CartItem.getCartItemsDataByIdCustomer(idUser);
            Double totalAmount = CartItem.getTotalAmountByUserId(idUser);
            Customer customer = Customer.getCustomerByIdUser(idUser);
            Double balance = customer.getBalance();
            HashMap<String, Object> promoHM = Promo.getPromoInfoByCode(promoCode);
            boolean isErrorOutOfStockException = false;

            if (cartItemsAndStock.size() <= 0) return "You do not have any cart items! Try add some.";

            for (int i = 0; i < cartItemsAndStock.size(); i++) {
                HashMap<String, Object> cartItemAndStock = cartItemsAndStock.get(i);
                if ((int) cartItemAndStock.get("stock") < (int) cartItemAndStock.get("count")) {
                    CartItem.updateCount(idUser, (String) cartItemAndStock.get("idProduct"), (Integer) cartItemAndStock.get("count"));
                }
            }

            if (isErrorOutOfStockException) return "One or more products in your cart exceed available stock.";

            totalAmount *= (100.0 - (Double) promoHM.get("discountPercentage"));
            if (balance < totalAmount) return "Your balance is not sufficient for the transaction. Please top up and checkout again.";

            Customer.updateBalanceByIdUser(idUser, (balance - totalAmount));
            String idOrder = OrderHeader.insert(idUser, (String) promoHM.get("idPromo"), totalAmount);

            for (int i = 0; i < cartItemsAndStock.size(); i++) {
                HashMap<String, Object> item = cartItemsAndStock.get(i);
                OrderDetail.insert(idOrder, (String) item.get("idProduct"), (Integer) item.get("count"));
            }
            CartItem.deleteAllByIdUser(idUser);
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your checkout is successful!";
    }
}
