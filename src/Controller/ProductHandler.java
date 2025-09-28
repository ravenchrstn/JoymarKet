package Controller;

import java.util.ArrayList;

import Models.Product;
public class ProductHandler {

    public ArrayList<Product> findProducts() {
        // diagram 2 - view products
        return Product.findProducts();
    }

}
