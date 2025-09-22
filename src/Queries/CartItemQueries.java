package Queries;

public class CartItemQueries {
    public static String generateDeleteCartItemQuery(String idCustomer, String idProduct) {
        return "DELETE FROM cart_items WHERE idCustomer = " + idCustomer + " AND idProduct = " + idProduct;
    }
}
