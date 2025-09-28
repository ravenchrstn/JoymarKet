package Models;

import DAs.CartItemDA;

public class CartItem {
    private String idUser, idProduct;
    private int count;
    protected static final CartItemDA cartItemDA = CartItemDA.getCartItemDA();

    public CartItem createCartItem(String idUser, String idProduct) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.count = 1;

        return this;
    }

    public static CartItem createCartItem() {
        return new CartItem();
    }

    public static String delete(String idUser, String idProduct) {
        // diagram 5 - remove cart item
        Integer rowsAffected = cartItemDA.deleteById(idUser, idProduct);
        if (rowsAffected <= 0) return "Cart is not deleted. Something is wrong.";
        return null;
    }
}