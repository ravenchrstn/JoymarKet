package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Auth.SessionManager;
import Models.Customer;
import Models.OrderHeader;
import Models.Promo;

public class OrderHeaderHandler {
    public HashMap<String, Object> createOrderHeader() throws IOException {
        // diagram 7
        Customer cust = (Customer) SessionManager.getUser();
        return cust.createOrderHeader();
    }

    public ArrayList<HashMap<String, Object>> getCustomerOrderHistories(String idCustomer) {
        // diagram 8 - view order history
        return OrderHeader.findCustomerOrderHistories(idCustomer);
    }

    public Promo getPromo(String code) {
        // diagram 7
        return Promo.getPromo(code);
    }

    public HashMap<String, Object> saveDataOrderHeader(String idProduct, int qty) {
        // diagram 7
        return saveDataOrderHeader(idProduct, qty);
    }

    public ArrayList<HashMap<String, Object>> getAllOrders() {
        // diagram 11 - view all orders
        return OrderHeader.findAllOrders();
    }
}
