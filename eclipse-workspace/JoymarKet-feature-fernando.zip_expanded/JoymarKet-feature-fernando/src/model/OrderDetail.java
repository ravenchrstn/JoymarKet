package model;

import java.sql.SQLException;

import da.OrderDetailDA;
import exception.NoRowsAffectedException;

public class OrderDetail {
    private String idOrder, idProduct;
    private Integer qty;
    public static final OrderDetailDA orderDetailDA = OrderDetailDA.getOrderDetailDA();
    
    public OrderDetail(String idOrder, String idProduct, Integer qty) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.qty = qty;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public Integer getQty() {
        return qty;
    }

    public static void insert(String idOrder, String idProduct, Integer qty) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        orderDetailDA.insert(idOrder, idProduct, qty);
    }
    
}
