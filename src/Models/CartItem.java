package Models;

import java.util.HashMap;

import DAs.CartItemDA;

public class CartItem {
    private String idCustomer, idProduct;
    private int count;
    protected static final CartItemDA cartItemDA = CartItemDA.getCartItemDA();

    public CartItem createCartItem(String idCustomer, String idProduct) {
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.count = 1;

        return this;
    }

    public Product getProduct(String idProduct) {
        // init(); ke Product
        // getData(String idProduct) ke Product
        return null;
    }

    public CartItem createCartItem(String idCustomer, String idProduct, int count) {
        // saveDA()
        return null;
    }

    public static HashMap<String, String> deleteCartItem(String idCustomer, String idProduct) {
        HashMap<String, String> returnHashMap = new HashMap<String, String>();
        cartItemDA.saveDA(idCustomer, idProduct);
        returnHashMap.put("idCustomer", idCustomer);
        returnHashMap.put("idProduct", idProduct);

        return returnHashMap;
    }
}

// public boolean deleteCartItem(String idCustomer, String idProduct) {
//     if (this.idCustomer == idCustomer && this.idProduct == idProduct) {
//         // kode mysql

//         return true;
//     }

//     return false;
// }

// public boolean editCartItem(String idCustomer, String idProduct, int count) {
//     this.idCustomer = idCustomer;
//     this.idProduct = idProduct;
//     this.count = count;

//     // kode mysql
//     return true;
// }

// public String getIdCustomer() {
//     return idCustomer;
// }

// public String getIdProduct() {
//     return idProduct;
// }

// public int getCount() {
//     return count;
// }