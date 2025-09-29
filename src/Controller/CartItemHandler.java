package Controller;

import java.sql.SQLException;

import Exceptions.NoRowsAffectedException;
import Models.CartItem;

public class CartItemHandler {

    public String delete(String idUser, String idProduct) {
        // diagram 5 - remove cart item
        try {
            CartItem.delete(idUser, idProduct);
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Cart item is successfully deleted.";
    }
}
