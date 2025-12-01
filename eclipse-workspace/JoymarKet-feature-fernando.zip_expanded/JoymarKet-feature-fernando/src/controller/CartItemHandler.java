package controller;

import java.sql.SQLException;

import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import helper.Checking;
import model.CartItem;
import model.Product;

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
    
    
    public String updateCartItemCount(String idUser, String idProduct, String countStr) {
        try {
            if (countStr == null || countStr.isEmpty())
                throw new InvalidInputException("Count empty.", "Please enter a quantity.");

            if (!Checking.isNumeric(countStr))
                throw new InvalidInputException("Not numeric.", "Quantity must be a number.");

            int count = Integer.parseInt(countStr);
            if (count < 1)
                throw new InvalidInputException("Invalid quantity.", "Quantity must be at least 1.");

            // Validate stock
            Product product = Product.getById(idProduct);
            if (product == null)
                throw new InvalidInputException("Product not found.", "This product no longer exists.");

            if (count > product.getStock())
                throw new InvalidInputException("Out of stock.", "Quantity exceeds available stock.");

            CartItem.updateCount(idUser, idProduct, count);

            return "Cart updated!";

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
