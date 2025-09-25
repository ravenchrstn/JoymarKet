package DAs;

import App.Connect;

public class CartItemDA {
    private Connect connect = Connect.getInstance();
    private static CartItemDA cartItemDA;

    public static CartItemDA getCartItemDA() {
        if (cartItemDA == null) cartItemDA = new CartItemDA();
        return cartItemDA;
    }

    public String delete(String idCustomer, String idProduct) {
        // diagram 5 - remove cart item
        String query = "DELETE FROM cart_items WHERE idCustomer = " + idCustomer + " AND idProduct = " + idProduct + ";";
        return this.connect.execUpdate(query);
    }
}
