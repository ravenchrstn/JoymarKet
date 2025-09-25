package Controller;

import java.io.IOException;
import java.util.HashMap;

import Auth.SessionManager;
import Models.CartItem;
import Models.Customer;

public class CartItemHandler {
    public HashMap<String, Object> getProduct(String idProduct) throws IOException {
        // diagram 3
        Customer customer = (Customer) SessionManager.getUser();
        HashMap<String, Object> customerCartItemHashMap = customer.createCartItem();

        

        return null;
    }

    public HashMap<String, String> deleteCartItem(String idCustomer, String idProduct) {
        // diagram 5
        return CartItem.deleteCartItem(idCustomer, idProduct);
    }
}
