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

    public HashMap<String, Object> getCustomerOrderHeader(String idOrder) {
        // return OrderDetail.
        return null;
    }

    public Promo getPromo(String code) {
        // diagram 7
        return Promo.getPromo(code);
    }

    public HashMap<String, Object> saveDataOrderHeader(String idProduct, int qty) {
        // diagram 7, belum jadi
        return saveDataOrderHeader(idProduct, qty);
    }

    public ArrayList<HashMap<String, Object>> getOrderHeaders() {
        // diagram 11
        return OrderHeader.getOrderHeaders();
    }
}
