package Controller;

import java.util.ArrayList;

import Helpers.Converting;
import Models.Product;
import Validators.ProductValidators;

public class ProductHandler {
    private ProductValidators productValidators;

    public ProductHandler(ProductValidators productValidators) {
        this.productValidators = productValidators;
    }

    public Product getProduct(String idProduct) {
        Integer idProductInteger = Converting.toInteger(idProduct);
        productValidators.validateId(idProductInteger);

        return Product.getProduct(idProduct);
    }

    public ArrayList<Product> getProducts() {
        // mungkin diperbaikin, karena gak sama dengan diagram 2
        return Product.getProducts();
    }

}
