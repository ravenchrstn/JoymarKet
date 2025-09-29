package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import Models.Product;
import Responses.MultipleObjectsResponse;
public class ProductHandler {

    public MultipleObjectsResponse getProducts() {
        // diagram 2 - view products
        try {
            ArrayList<Product> products = Product.getProducts();
            if (products.isEmpty() == true) return new MultipleObjectsResponse<>("Product is not available right now.", new ArrayList<>()); 
            return new MultipleObjectsResponse<>("", products);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse<>("An error occured while processing your request. Please try again later.", new ArrayList<>());
        }
    }

}
