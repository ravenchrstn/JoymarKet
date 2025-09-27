package Controller;

import java.io.IOException;
import java.util.HashMap;
import Models.CartItem;

public class CartItemHandler {
    public HashMap<String, Object> getProduct(String idProduct) throws IOException {
        // diagram 3

        return null;
    }

    public String deleteCartItem(String idCustomer, String idProduct) {
        // diagram 5 - remove cart item

        return CartItem.deleteCartItem(idCustomer, idProduct);
    }
}
