package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.Connect;
import model.Product;

public class ProductDA {
    private Connect connect = Connect.getInstance();
    private static ProductDA productDA = getProductDA();

    public static ProductDA getProductDA() {
        if (productDA == null) productDA = new ProductDA();
        return productDA;
    }

    public ArrayList<Product> findAll() throws SQLException {
        // diagram 2 - view products
        String query = "SELECT idProduct, name, category, price, stock FROM product";
        ResultSet rs = this.connect.execQuery(query);

        ArrayList<Product> products = new ArrayList<Product>();
        while (rs.next()) {
            Product product = Product.fromResultSet(rs);
            products.add(product);
        }
        return products;
    }
}
