package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;
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
        String query = "SELECT idProduct, name, category, price, stock FROM products";
        ResultSet rs = this.connect.execQuery(query);

        ArrayList<Product> products = new ArrayList<Product>();
        while (rs.next()) {
            Product product = Product.fromResultSet(rs);
            products.add(product);
        }
        return products;
    }
    
    public Product findById(String idProduct) throws SQLException {
        String query = String.format(
            "SELECT idProduct, name, category, price, stock FROM products WHERE idProduct = '%s';",
            idProduct
        );
        ResultSet rs = this.connect.execQuery(query);
        if (rs.next()) {
            return Product.fromResultSet(rs);
        }
        return null;
    }
    
    public void updateStock(String idProduct, Integer stock)
            throws SQLException, NoRowsAffectedException {

        String query = String.format(
            "UPDATE products SET stock = %d WHERE idProduct = '%s';",
            stock, idProduct
        );
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        if (rs != null) {
            try { rs.next(); } catch (SQLException ignore) {}
        }
        if ((Integer) hm.get("rowsAffected") <= 0) {
            throw new NoRowsAffectedException("The product you are trying to update was not found.");
        }
    }
}
