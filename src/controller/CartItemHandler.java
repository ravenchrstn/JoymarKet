package controller;

import java.sql.SQLException;

import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import model.CartItem;
import model.Product;

public class CartItemHandler {

	public String addToCart(String idUser, String idProduct, int amount) throws InvalidInputException {
		Product p = Product.getProductDA(idProduct);
        if (amount <= 0) throw new InvalidInputException("Count cannot be 0.", "Count must be more than 0.");
        if (amount > p.getStock())  throw new InvalidInputException("Low Stock.", "Stock not enough.");

        try {
            CartItem.add(idUser, idProduct, amount);
        } catch (NoRowsAffectedException e) {
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred while adding to cart.";
        }
        return "Successfully added to cart!";
    }
	
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
