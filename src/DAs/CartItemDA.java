package DAs;

import java.util.HashMap;

import App.Connect;
import Queries.CartItemQueries;

public class CartItemDA {
    private Connect connect = Connect.getInstance();
    private static CartItemDA cartItemDA;

    public static CartItemDA getCartItemDA() {
        if (cartItemDA == null) cartItemDA = new CartItemDA();
        return cartItemDA;
    }

    public HashMap<String, Object> saveDA(String idCustomer, String idProduct) {
        // delete
        String query = CartItemQueries.generateDeleteCartItemQuery(idCustomer, idProduct);
        return this.connect.execUpdate(query);
    }
}
