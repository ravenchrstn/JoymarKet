package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.ProductDA;

public class Product {
    private String idProduct, name, category;
    private Double price;
    private int stock;
    private static final ProductDA productDA = ProductDA.getProductDA();

    public Product(String idProduct, String name, String category, Double price, int stock) {
        this.idProduct = idProduct;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public static Product fromResultSet(ResultSet rs) {
        try {
            return new Product(rs.getString("idProduct"), rs.getString("name"), rs.getString("category"), rs.getDouble("price"), rs.getInt("stock"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Product getProduct(String idProduct) { // HERE
        // return objek Product karena sudah mencover semua informasi yang harus dikembalikan
        return productDA.read(idProduct);
    }

    public static ArrayList<Product> getProducts() {
        // diagram 2 - view products
        return productDA.getProducts();
    }

    public String getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public static Product getAvailableProduct(String idProduct) { // HERE
        return productDA.readAvailable(idProduct);
    }

    public static HashMap<String, Object> editProductStock(String idProduct, int stock) { // HERE
        return productDA.saveDA(idProduct, stock);
    }
}