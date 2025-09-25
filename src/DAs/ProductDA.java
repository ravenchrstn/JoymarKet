package DAs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import App.Connect;
import Helpers.Result;
import Models.Product;
import Queries.ProductQueries;

public class ProductDA {
    private Connect connect = Connect.getInstance();
    private static ProductDA productDA = getProductDA();

    public static ProductDA getProductDA() {
        if (productDA == null) productDA = new ProductDA();
        return productDA;
    }

    public ArrayList<Product> getProducts() {
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

    public Product read(String idProduct) {
        String query = ProductQueries.generateReadQuery(idProduct);
        Result res = this.connect.execQuery(query);
        ResultSet rs = res.getRs();

        return Product.fromResultSet(rs);
    }

    public Product readAvailable(String idProduct) {
        String query = ProductQueries.generateReadQuery(idProduct);
        Result res = this.connect.execQuery(query);
        ResultSet rs = res.getRs();

        return Product.fromResultSet(rs);
    }

    public HashMap<String, Object> saveDA(String idProduct, int stock) {
        // edit product stock
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        this.connect.execUpdate(idProduct);

        hashMap.put("idProduct", idProduct);
        hashMap.put("stock", stock); // autoboxing (otomatis mengkonversi primitive -> wrapper class)
        return hashMap;
    }

    // public Product HashMap<String, Object> saveDA(Product product) {
    //     int rowsAffected = 0;
    //     String query = ProductQueries.generateAvailableReadQuery();
    //     HashMap<String, Object> hashMap = this.connect.execUpdate(query);
    //     rowsAffected += (int) hashMap.get("rowsAffected");
    //     products.get(i).setIdProduct((String) hashMap.get("generatedKeys"));
    //     return rowsAffected;
    // }

    // public <R extends Product> int saveDA(R product, String query) {
    //     HashMap<String, Object> hashMap = this.connect.execUpdate(query);
    //     product.setIdProduct((String) hashMap.get("generatedKeys"));
    //     return (int) hashMap.get("rowsAffected");
    // }

    // public <R extends Product> int saveDA(String query) {
    //     HashMap<String, Object> hashMap = this.connect.execUpdate(query);
    //     return (int) hashMap.get("rowsAffected");
    // }
}
