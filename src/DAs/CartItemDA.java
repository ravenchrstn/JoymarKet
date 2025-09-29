package DAs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import App.Connect;
import Exceptions.NoRowsAffectedException;
public class CartItemDA {
    private Connect connect = Connect.getInstance();
    private static CartItemDA cartItemDA;

    public static CartItemDA getCartItemDA() {
        if (cartItemDA == null) cartItemDA = new CartItemDA();
        return cartItemDA;
    }

    public void deleteById(String idUser, String idProduct) throws NoRowsAffectedException, SQLException {
        // diagram 5 - remove cart item
        String query = "DELETE FROM cart_items WHERE idUser = " + idUser + " AND idProduct = " + idProduct + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        rs.next();
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("The item you are trying to delete was not found or has already been removed.");
    }

    public ArrayList<HashMap<String, Object>> findAllCheckoutItemsByIdUser(String idUser) throws SQLException {
        // diagram 7 - checkout and place order
        String query = "SELECT p.idProduct, ci.count FROM cart_items ci JOIN products p ON ci.idProduct = p.idProduct WHERE ci.idUser = " + idUser + ";";
        ResultSet rs = this.connect.execQuery(query);
        ArrayList<HashMap<String, Object>> checkoutItems = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("idProduct", rs.getString("idProduct"));
            hm.put("count", rs.getInt("count"));
            checkoutItems.add(hm);
        }
        
        return checkoutItems;
    }

    public Double sumTotalAmountByIdUser(String idUser) {
        // diagram 7 - checkout and place order
        String query = "SELECT SUM(ci.count * p.price) AS totalAmount FROM cart_items ci JOIN products p ON ci.idProduct = p.idProduct WHERE ci.idUser = " + idUser + " GROUP BY ci.idProduct";
        try {
            return this.connect.execQuery(query).getDouble("totalAmount");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAllByIdUser(String idUser) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        String query = "DELETE FROM cart_items WHERE idUser = " + idUser + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        rs.next();
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Your cart is already empty.");
    }
}
