package Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.OrderHeaderDA;
import Queries.OrderHeaderQueries;

public class OrderHeader {
    private String idOrder, idCustomer, idPromo, status;
    private Date orderedAt;
    private Double totalAmount;
    private static final OrderHeaderDA orderHeaderDA = OrderHeaderDA.getOrderHeaderDA();

    public OrderHeader(String idOrder, String idCustomer, String idPromo, String status, Date orderedAt, Double totalAmount) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idPromo = idPromo;
        this.status = status;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
    }

    public static HashMap<String, Object> createOrderHeader(String idCustomer, String idPromo) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("OrderHeader", new OrderHeader(null, idCustomer, idPromo, "Pending", null, 0));
        hashMap.put("idCustomer", idCustomer);
        return hashMap;
    }

    public HashMap<String, Object> createOrderHeader() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("OrderHeader", new OrderHeader(null, idCustomer, idPromo, "Pending", null, 0));
        hashMap.put("idCustomer", idCustomer);
        return hashMap;
    }

    public static boolean editOrderHeaderStatus(String idOrder, String status) {
        int rowsAffected = orderHeaderDA.updateStatus(idOrder, status);

        if (rowsAffected > 0) return true;
        return false;
    }

    public static OrderHeader getOrderHeader(String idOrder) {
        return orderHeaderDA.read(OrderHeaderQueries.generateReadQuery(idOrder)).get(0);
    }

    public static ArrayList<OrderHeader> getCustomerOrderHeader(String idOrder, String idCustomer) {
        // perbaikin ini, diagram 8
        return orderHeaderDA.read(OrderHeaderQueries.generateReadCustomerOrderHeaderQuery(idOrder, idCustomer));
    }

    public OrderHeader saveDataOrderHeader(String idProduct, int qty) {        
        orderHeaderDA.saveDA(idOrder, idProduct, qty);
        this.totalAmount = orderHeaderDA.readTotalAmount(idProduct);

        // orderDetail.saveOrderDetail(idProduct, qty)
        return this;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public String getIdPromo() {
        return idPromo;
    }

    public String getStatus() {
        return status;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

}
