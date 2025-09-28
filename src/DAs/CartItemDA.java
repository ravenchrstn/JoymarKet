package DAs;

import App.Connect;

public class CartItemDA {
    private Connect connect = Connect.getInstance();
    private static CartItemDA cartItemDA;

    public static CartItemDA getCartItemDA() {
        if (cartItemDA == null) cartItemDA = new CartItemDA();
        return cartItemDA;
    }

    public Integer deleteById(String idUser, String idProduct) {
        // diagram 5 - remove cart item
        String query = "DELETE FROM cart_items WHERE idUser = " + idUser + " AND idProduct = " + idProduct + ";";
        return this.connect.execUpdate(query);
    }
}
