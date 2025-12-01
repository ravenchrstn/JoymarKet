package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import exception.InvalidInputException;
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
    
    // update product stock (diagram 9):
    public String updateProductStock(String idProduct, String stockStr) {
        try {
            if (idProduct == null || idProduct.isEmpty())
                throw new InvalidInputException("Invalid product.", "Please select a product.");

            if (stockStr == null || stockStr.isEmpty())
                throw new InvalidInputException("Stock empty.", "Please enter a stock value.");

            if (!Checking.isNumeric(stockStr))
                throw new InvalidInputException("Not numeric.", "Stock must be numeric.");

            int stock = (int) Double.parseDouble(stockStr);
            if (stock < 0)
                throw new InvalidInputException("Negative stock.", "Stock cannot be negative.");

            Product p = Product.getById(idProduct);
            if (p == null)
                throw new InvalidInputException("Product not found.", "The product does not exist.");

            Product.updateStock(idProduct, stock);

            return "Stock updated successfully.";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
