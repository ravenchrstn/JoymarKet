package Models;

import DAs.OrderDetailDA;

public class OrderDetail {
    private String idOrder, idProduct;
    private int qty;
    public static final OrderDetailDA orderDetailDA = OrderDetailDA.getOrderDetailDA();

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
