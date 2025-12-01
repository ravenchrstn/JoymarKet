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
    
    // find by id: diagram 9
    public Product findById(String idProduct) throws SQLException {
        String query = String.format(
            "SELECT * FROM product WHERE idProduct = '%s';", 
            idProduct
        );

        ResultSet rs = connect.execQuery(query);
        if (!rs.next()) return null;

        return Product.fromResultSet(rs);
    }
    
    // update product stock: diagram 9
    public void updateStock(String idProduct, int stock) throws SQLException {
        String query = String.format(
            "UPDATE product SET stock = %d WHERE idProduct = '%s';",
            stock, idProduct
        );
        connect.execUpdate(query);
    }
}
