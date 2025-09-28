package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static ArrayList<Product> findProducts() {
        // diagram 2 - view products
        return productDA.findAll();
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
}