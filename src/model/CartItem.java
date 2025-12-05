package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import da.CartItemDA;
import exception.NoRowsAffectedException;

public class CartItem {
    private String idUser, idProduct;
    private Integer count;

    private static final CartItemDA cartItemDA = CartItemDA.getCartItemDA();
    
    public CartItem(String idUser, String idProduct, Integer count) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.count = count;
    }

	public static void add(String idUser2, String idProduct2, int amount)  throws SQLException, NoRowsAffectedException{
		cartItemDA.addProductToCart(idUser2, idProduct2, amount);
	}
    
    public static CartItem fromResultSet(ResultSet rs) throws SQLException {
        return new CartItem(rs.getString("idUser"), rs.getString("idProduct"), rs.getInt("count"));
    }

    public static void delete(String idUser, String idProduct) throws NoRowsAffectedException, SQLException {
        // diagram 5 - remove cart item
        cartItemDA.deleteById(idUser, idProduct);
    }

    public static ArrayList<HashMap<String, Object>> getCartItemsDataByIdCustomer(String idUser) throws SQLException {
        // diagram 7 - checkout and place order
        return cartItemDA.findCartItemsDataByIdCustomer(idUser);
    }

    public static void deleteAllByIdUser(String idUser) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        cartItemDA.deleteAllByIdCustomer(idUser);
    }

    public static Double getTotalAmountByUserId(String idUser) {
        // diagram 7 - checkout and place order
        return cartItemDA.sumTotalAmountByIdCustomer(idUser);
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

//	public static ArrayList<HashMap<String, Object>> getCartItemsAndStockByIdUser(String idUser2) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}