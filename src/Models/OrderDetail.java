package Models;

import java.util.ArrayList;
import java.util.HashMap;

import DAs.OrderDetailDA;
import Helpers.Result;

public class OrderDetail {
    private String idOrder, idProduct;
    private int qty;
    public static final OrderDetailDA orderDetailDA = OrderDetailDA.getOrderDetailDA();

    // public boolean createOrderDetail(String idOrder, String idProduct, int qty) {
        // masih gatau, rasanya ini cuman salah nama untuk saveOrderDetail
    //     this.idOrder = idOrder;
    //     this.idProduct = idProduct;
    //     this.qty = qty;

    //     return true;
    // }

    public static Result getIdsProduct(ArrayList<String> idsOrder) {
        // diagram 11, tidak ada di diagram
        // read one order detail by idOrder
        return orderDetailDA.read(idsOrder);
    }

    public HashMap<String, Object> getOrderDetail(String idOrder, String idProduct) {
        // diagram 11
        // orderDetailDA.read(idOrder, idProduct);

        Product product = Product.getProduct(idProduct);
        HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
        returnHashMap.put("idOrder", idOrder);
        returnHashMap.put("idProduct", product.getIdProduct());
        returnHashMap.put("name", product.getName());
        returnHashMap.put("price", product.getPrice());
        returnHashMap.put("stock", product.getStock());
        returnHashMap.put("category", product.getCategory());
        
        return returnHashMap;
    }

    public static HashMap<String, Object> saveOrderDetail(String idOrder, String idProduct, int qty) {
        // diagram 7, membuat order detail baru
        orderDetailDA.saveDetailDA(idOrder, idProduct, qty);

        HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
        returnHashMap.put("idProduct", idProduct);
        returnHashMap.put("qty", qty);
        return returnHashMap;
    }   

    public static HashMap<String, Object> getCustomerOrderDetail(String idOrder, String idProduct) {
        // diagram 8
        // return idOrder, idProduct, name, price, stock, category
        Product productHashMap = Product.getProduct(idProduct);
        HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
        returnHashMap.put("idOrder", idOrder);
        returnHashMap.put("idProduct", productHashMap.getIdProduct());
        returnHashMap.put("name", productHashMap.getName());
        returnHashMap.put("price", productHashMap.getPrice());
        returnHashMap.put("stock", productHashMap.getStock());
        returnHashMap.put("category", productHashMap.getCategory());
        
        return returnHashMap;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public int getQty() {
        return qty;
    }
    
}
