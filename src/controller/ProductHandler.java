package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import helper.Checking;
import model.Product;
import response.MultipleObjectsResponse;
public class ProductHandler {

    public MultipleObjectsResponse getProducts() {
        // diagram 2 - view products
        try {
            ArrayList<Product> products = Product.getAllProducts();
            if (products.isEmpty() == true) return new MultipleObjectsResponse<>("Product is not available right now.", new ArrayList<>()); 
            return new MultipleObjectsResponse<>("", products);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse<>("An error occured while processing your request. Please try again later.", new ArrayList<>());
        }
    }
    
    // Admin edit product stock
    public String updateProductStock(String idProduct, String stockInput) {
        try {
            if (idProduct == null || idProduct.equals("")) {
                throw new InvalidInputException(
                    "Product ID is empty.",
                    "Please select a product to update."
                );
            }
            if (stockInput == null || stockInput.equals("")) {
                throw new InvalidInputException(
                    "Stock is empty.",
                    "Stock value cannot be empty."
                );
            }
            if (!Checking.isNumeric(stockInput)) {
                throw new InvalidInputException(
                    "Stock is not numeric.",
                    "Stock must be a numeric value."
                );
            }
            int stock = (int) Double.parseDouble(stockInput);
            if (stock < 0) {
                throw new InvalidInputException(
                    "Stock is negative.",
                    "Stock cannot be negative."
                );
            }
            // ensure product exists
            Product p = Product.getById(idProduct);
            if (p == null) {
                throw new InvalidInputException(
                    "Product not found.",
                    "The selected product does not exist."
                );
            }
            Product.updateStock(idProduct, stock);
        } catch (InvalidInputException e) {
            return e.getUserMessage();
        } catch (NoRowsAffectedException e) {
            e.printStackTrace();
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Product stock is successfully updated.";
    }

}
