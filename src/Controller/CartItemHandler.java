package Controller;

import java.util.HashMap;

import Models.CartItem;

public class CartItemHandler {
    public HashMap<String, Object> getProduct(String idProduct) {
        // diagram 3
        return null;
    }

    public HashMap<String, String> deleteCartItem(String idCustomer, String idProduct) {
        // diagram 5
        return CartItem.deleteCartItem(idCustomer, idProduct);
    }
}
