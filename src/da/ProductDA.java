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
        String query = "SELECT idProduct, name, category, price, stock FROM product";
        ResultSet rs = this.connect.execQuery(query);

        ArrayList<Product> products = new ArrayList<Product>();
        while (rs.next()) {
            Product product = Product.fromResultSet(rs);
            products.add(product);
        }
        return products;
    }
    
    public Product findById(String idProduct) throws SQLException {
        String query = "SELECT * FROM product WHERE idProduct = '" + idProduct + "'";
        ResultSet rs = connect.execQuery(query);

        if (rs.next()) {
            return Product.fromResultSet(rs);
        }
        return null;
    }

    public void reduceStock(String idProduct, int amount) throws SQLException, NoRowsAffectedException {
        String query = "UPDATE product SET stock = stock - " + amount + " WHERE idProduct = '" + idProduct + "' AND stock >= " + amount + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        int rows = (Integer) hm.get("rowsAffected");
        if (rows <= 0) {
            throw new NoRowsAffectedException("Not enough stock for product " + idProduct);
        }
    }
    
    public void updateStock(String idProduct, int newStock) throws SQLException, NoRowsAffectedException {
        String query = "UPDATE product SET stock = " + newStock + " WHERE idProduct = '" + idProduct + "'";
        HashMap<String, Object> hm = connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0) {
            throw new NoRowsAffectedException("Failed to update product stock.");
        }
    }

}
