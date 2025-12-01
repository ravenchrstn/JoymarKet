package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.Connect;
import exception.NoRowsAffectedException;
import model.CartItem;

public class CartItemDA {

    private Connect connect = Connect.getInstance();
    private static CartItemDA cartItemDA;

    public static CartItemDA getCartItemDA() {
        if (cartItemDA == null) cartItemDA = new CartItemDA();
        return cartItemDA;
    }

    // Delete a cart item (Diagram 5)
    public void deleteById(String idUser, String idProduct) throws NoRowsAffectedException, SQLException {
        String query = String.format(
            "DELETE FROM cart_item WHERE idUser = '%s' AND idProduct = '%s';",
            idUser, idProduct
        );

        HashMap<String, Object> hm = this.connect.execUpdate(query);

        if ((Integer) hm.get("rowsAffected") <= 0)
            throw new NoRowsAffectedException("The item you are trying to delete was not found.");
    }

    // Get cart for checkout (Diagram 7)
    public ArrayList<HashMap<String, Object>> findCartItemsAndStockByIdUser(String idUser) throws SQLException {
        String query = String.format(
            "SELECT p.idProduct, p.name, ci.count, p.stock FROM cart_item ci " +
            "JOIN products p ON ci.idProduct = p.idProduct " +
            "WHERE ci.idUser = '%s' ORDER BY p.idProduct ASC;",
            idUser
        );

        ResultSet rs = this.connect.execQuery(query);
        ArrayList<HashMap<String, Object>> checkoutItems = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("idProduct", rs.getString("idProduct"));
            hm.put("name", rs.getString("name"));
            hm.put("count", rs.getInt("count"));
            hm.put("stock", rs.getInt("stock"));
            checkoutItems.add(hm);
        }

        return checkoutItems;
    }

    // Total amount (Diagram 7)
    public Double sumTotalAmountByIdUser(String idUser) {
        String query = String.format(
            "SELECT SUM(ci.count * p.price) AS totalAmount FROM cart_item ci " +
            "JOIN products p ON ci.idProduct = p.idProduct WHERE ci.idUser = '%s';",
            idUser
        );

        try {
            ResultSet rs = this.connect.execQuery(query);
            if (rs.next()) return rs.getDouble("totalAmount");
            return 0.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // Delete all cart items after checkout
    public void deleteAllByIdUser(String idUser) throws NoRowsAffectedException, SQLException {
        String query = String.format(
            "DELETE FROM cart_item WHERE idUser = '%s';",
            idUser
        );

        HashMap<String, Object> hm = this.connect.execUpdate(query);

        if ((Integer) hm.get("rowsAffected") <= 0)
            throw new NoRowsAffectedException("Your cart is already empty.");
    }

    // UPDATE count
    public void updateCount(String idUser, String idProduct, int count) throws SQLException {
        String query = String.format(
            "UPDATE cart_item SET count = %d WHERE idUser = '%s' AND idProduct = '%s';",
            count, idUser, idProduct
        );
        connect.execUpdate(query);
    }
    
    // Get cart items for CartPage
    public List<CartItem> getCartItemsByUser(String idUser) throws SQLException {
        String query = String.format(
            "SELECT * FROM cart_item WHERE idUser = '%s';",
            idUser
        );

        ResultSet rs = this.connect.execQuery(query);
        List<CartItem> list = new ArrayList<>();

        while (rs.next()) {
            list.add(CartItem.fromResultSet(rs));
        }

        return list;
    }
}