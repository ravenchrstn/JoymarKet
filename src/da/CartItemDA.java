package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;
public class CartItemDA {
    private Connect connect = Connect.getInstance();
    private static CartItemDA cartItemDA;

    public static CartItemDA getCartItemDA() {
        if (cartItemDA == null) cartItemDA = new CartItemDA();
        return cartItemDA;
    }
    
    public void addProductToCart(String idCustomer, String idProduct, int amount) throws NoRowsAffectedException, SQLException {
    	String query = 
    		    "INSERT INTO cartitem (idCustomer, idProduct, count) VALUES ('"
    		    + idCustomer + "', '"
    		    + idProduct + "', "
    		    + amount + ") "
    		    + "ON DUPLICATE KEY UPDATE count = count + " + amount + ";";
    	
	    HashMap<String, Object> hm = this.connect.execUpdate(query);
	    int rowsAffected = (Integer) hm.get("rowsAffected");

	    if (rowsAffected <= 0) {
	        throw new NoRowsAffectedException("Failed to add product to cart.");
	    }
    }

    public void deleteById(String idCustomer, String idProduct) throws NoRowsAffectedException, SQLException {
        // diagram 5 - remove cart item
        String query = "DELETE FROM cartitem WHERE idCustomer = " + idCustomer + " AND idProduct = " + idProduct + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("The item you are trying to delete was not found or has already been removed.");
    }

    public ArrayList<HashMap<String, Object>> findCartItemsDataByIdCustomer(String idCustomer) throws SQLException {
        // diagram 7 - checkout and place order
        String query = "SELECT p.idProduct, p.name, ci.count, p.stock, p.price, p.category FROM cartitem ci JOIN product p ON ci.idProduct = p.idProduct WHERE ci.idCustomer = " + idCustomer + " ORDER BY p.idProduct ASC;";
        ResultSet rs = this.connect.execQuery(query);
        ArrayList<HashMap<String, Object>> checkoutItems = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("idProduct", rs.getString("idProduct"));
            hm.put("name", rs.getString("name"));
            hm.put("count", rs.getInt("count"));
            hm.put("price", rs.getInt("price"));
            hm.put("category", rs.getString("category"));
            hm.put("stock", rs.getInt("stock"));
            checkoutItems.add(hm);
        }
        
        return checkoutItems;
    }

    public Double sumTotalAmountByIdCustomer(String idCustomer) {
        // diagram 7 - checkout and place order
        String query = "SELECT SUM(ci.count * p.price) AS totalAmount FROM cartitem ci JOIN product p ON ci.idProduct = p.idProduct WHERE ci.idCustomer = " + idCustomer;
        ResultSet rs = this.connect.execQuery(query);
        try {
        	if (rs.next()) {
        		return rs.getDouble("totalAmount");
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllByIdCustomer(String idCustomer) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        String query = "DELETE FROM cartitem WHERE idCustomer = " + idCustomer + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Your cart is already empty.");
    }

    public void updateCount(String idCustomer, String idProduct, Integer count) throws SQLException, NoRowsAffectedException {
        // diagram 7 - checkout and place order
        String query = "UPDATE cartitem SET count = " + count + " WHERE idCustomer = " + idCustomer + " AND idProduct = " + idProduct + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Your cart is already empty.");
    }
}
