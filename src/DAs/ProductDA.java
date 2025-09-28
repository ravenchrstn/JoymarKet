package DAs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import App.Connect;
import Helpers.Result;
import Models.Product;

public class ProductDA {
    private Connect connect = Connect.getInstance();
    private static ProductDA productDA = getProductDA();

    public static ProductDA getProductDA() {
        if (productDA == null) productDA = new ProductDA();
        return productDA;
    }

    public ArrayList<Product> findAll() {
        // diagram 2 - view products
        String query = "SELECT idProduct, name, category, price, stock FROM products";
        Result res = this.connect.execQuery(query);
        ResultSet rs = res.getRs();

        ArrayList<Product> products = new ArrayList<Product>();
        try {
            while (rs.next()) {
                Product product = Product.fromResultSet(rs);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public HashMap<String, Object> saveDA(String idProduct, int stock) {
        // edit product stock
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        this.connect.execUpdate(idProduct);

        hashMap.put("idProduct", idProduct);
        hashMap.put("stock", stock); // autoboxing (otomatis mengkonversi primitive -> wrapper class)
        return hashMap;
    }
}
