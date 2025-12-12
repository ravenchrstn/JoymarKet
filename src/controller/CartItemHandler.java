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
    
 // Update cart item quantity
    public String updateCount(String idUser, String idProduct, String countInput) {
        try {
            if (idUser == null || idUser.equals("")) {
                throw new InvalidInputException(
                    "User ID is empty.",
                    "User is not valid. Please log in again."
                );
            }

            if (idProduct == null || idProduct.equals("")) {
                throw new InvalidInputException(
                    "Product ID is empty.",
                    "Please select a cart item to update."
                );
            }

            if (countInput == null || countInput.equals("")) {
                throw new InvalidInputException(
                    "Count is empty.",
                    "Quantity cannot be empty."
                );
            }

            if (!Checking.isNumeric(countInput)) {
                throw new InvalidInputException(
                    "Count is not numeric.",
                    "Quantity must be a numeric value."
                );
            }

            int count = (int) Double.parseDouble(countInput);
            if (count <= 0) {
                throw new InvalidInputException(
                    "Count is not positive.",
                    "Quantity must be at least 1."
                );
            }

            // check against current stock
            Product p = Product.getById(idProduct);
            if (p == null) {
                throw new InvalidInputException(
                    "Product not found.",
                    "The product in this cart item no longer exists."
                );
            }
            if (p.getStock() != null && count > p.getStock()) {
                throw new InvalidInputException(
                    "Count exceeds stock.",
                    "Quantity cannot be greater than available stock (" + p.getStock() + ")."
                );
            }

            CartItem.updateCount(idUser, idProduct, count);
        } catch (InvalidInputException e) {
            return e.getUserMessage();
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }

        return "Cart item is successfully updated.";
    }
}
