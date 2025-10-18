package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import da.ProductDA;

public class Product {
    private String idProduct, name, category;
    private Double price;
    private Integer stock;
    private static final ProductDA productDA = ProductDA.getProductDA();

    public Product(String idProduct, String name, String category, Double price, Integer stock) {
        this.idProduct = idProduct;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public static Product fromResultSet(ResultSet rs) throws SQLException {
        return new Product(rs.getString("idProduct"), rs.getString("name"), rs.getString("category"), rs.getDouble("price"), rs.getInt("stock"));
    }

    public static ArrayList<Product> getAllProducts() throws SQLException {
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

    public Integer getStock() {
        return stock;
    }
}