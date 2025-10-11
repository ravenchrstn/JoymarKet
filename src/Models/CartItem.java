package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.CartItemDA;
import Exceptions.NoRowsAffectedException;

public class CartItem {
    private String idUser, idProduct;
    private Integer count;
    protected static final CartItemDA cartItemDA = CartItemDA.getCartItemDA();
    
    public CartItem(String idUser, String idProduct, Integer count) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.count = count;
    }

    public static CartItem fromResultSet(ResultSet rs) throws SQLException {
        return new CartItem(rs.getString("idUser"), rs.getString("idProduct"), rs.getInt("count"));
    }

    public static void delete(String idUser, String idProduct) throws NoRowsAffectedException, SQLException {
        // diagram 5 - remove cart item
        cartItemDA.deleteById(idUser, idProduct);
    }

    public static ArrayList<HashMap<String, Object>> getCartItemsAndStockByIdUser(String idUser) throws SQLException {
        // diagram 7 - checkout and place order
        return cartItemDA.findCartItemsAndStockByIdUser(idUser);
    }

    public static void deleteAllByIdUser(String idUser) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        cartItemDA.deleteAllByIdUser(idUser);
    }

    public static Double getTotalAmountByUserId(String idUser) {
        // diagram 7 - checkout and place order
        return cartItemDA.sumTotalAmountByIdUser(idUser);
    }

    public static void updateCount(String idUser, String idProduct, Integer count) throws SQLException, NoRowsAffectedException {
        // diagram 7 - checkout and place order
        cartItemDA.updateCount(idUser, idProduct, count);
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public Integer getCount() {
        return count;
    }

}