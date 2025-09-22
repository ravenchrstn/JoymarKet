package Queries;

import java.util.ArrayList;

import Models.User;

public class ProductQueries {

    public static String generateReadQuery() {
        return "SELECT idProduct, name, category, price, stock FROM products";
    }

    public static String generateReadQuery(String idProduct) {
        return "SELECT idProduct, name, category, price, stock FROM products WHERE idProduct = " + idProduct + " LIMIT 1";
    }

    public static String generateReadQuery(ArrayList<String> idsProduct) {
        String query = "SELECT idProduct, name, category, price, stock FROM users WHERE idProduct in (";
        for (int i = 0; i < idsProduct.size(); i++) {
            query += idsProduct.get(i) + ", ";
        }
        query += ")";
        return query;
    }

    public static String generateAvailableReadQuery(String idProduct) {
        return "SELECT idProduct, name, category, price, stock FROM products WHERE idProduct = " + idProduct + "AND stock > 0" + " LIMIT 1";
    }

    public static String generateAvailableReadQuery(ArrayList<String> idsProduct) {
        String query = "SELECT idProduct, name, category, price, stock FROM users WHERE stock > 0 AND idProduct in (";
        for (int i = 0; i < idsProduct.size(); i++) {
            query += idsProduct.get(i) + ", ";
        }
        query += ")";
        return query;
    }

    public static String generateRegisterQuery(User user) {
        return "INSERT INTO users () VALUES (" + user.getFullName() + ", " + user.getEmail() + ", " + user.getPassword() + ", " + user.getPhone() + ", " + user.getAddress() + ")";
    }

    // public static String generateUpdateStockQuery(Product product) {
    //     return "ALTER products SET stock = " + product.stock + " WHERE idProduct = " + product.idProduct + " AND name = " + product.name + " AND category = " + product.category + " AND price = " + product.price;
    // }

    public static String generateUpdateStockQuery(String idProduct, int stock) {
        return "UPDATE products SET stock = " + stock + " WHERE idProduct = " + idProduct;
    }
}
