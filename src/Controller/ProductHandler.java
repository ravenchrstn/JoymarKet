package Controller;

import java.util.ArrayList;

import Models.Product;
public class ProductHandler {

    public Product getProduct(String idProduct) {
        return Product.getProduct(idProduct);
    }

    public ArrayList<Product> getProducts() {
        // diagram 2 - view products
        return Product.getProducts();
    }

}
