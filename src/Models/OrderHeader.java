package Models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.OrderDetailDA;
import DAs.OrderHeaderDA;
import Helpers.Result;
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

    public static OrderHeader fromResultSet(ResultSet rs) {
        try {
            return new OrderHeader(rs.getString("idOrder"), rs.getString("idCustomer"), rs.getString("idPromo"), rs.getString("status"), rs.getDate("orderedAt"), rs.getDouble("totalAmount"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap<String, Object> createOrderHeader(String idCustomer, String idPromo) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("OrderHeader", new OrderHeader(null, idCustomer, idPromo, "Pending", null, 0.0));
        hashMap.put("idCustomer", idCustomer);
        return hashMap;
    }

    public static OrderHeader createOrderHeader() {
        OrderHeader orderHeader = new OrderHeader(null, null, null, null, null, null);
        return orderHeader;
    }

    public static boolean editOrderHeaderStatus(String idOrder, String status) {
        int rowsAffected = orderHeaderDA.updateStatus(idOrder, status);

        if (rowsAffected > 0) return true;
        return false;
    }

    public static ArrayList<HashMap<String, Object>> getOrderHeaders() {
        // diagram 11
        ArrayList<OrderHeader> ohs = orderHeaderDA.read();
        ArrayList<String> idsOrder = new ArrayList<String>();
        for (int i = 0; i < ohs.size(); i++) {
            idsOrder.add(ohs.get(i).getIdOrder());
        }

        ArrayList<String> idsProduct = new ArrayList<String>();
        Result res = OrderDetail.getIdsProduct(idsOrder);
        try {
            while (res.getRs().next()) {
                String idProduct = res.getRs().getString("idProduct");
                idsProduct.add(idProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<OrderDetail> ods = new ArrayList<OrderDetail>();
        
        for (int i = 0; i < idsProduct.size(); i++) {
            // OrderDetail od = 
            // ods.add(null)
        }
    }

    public static HashMap<String, String> getCustomerOrderHeader(String idOrder) {
        // diagram 8, ngetsuck
        
        // return OrderDetail.getCustomerOrderDetail(idOrder, );
    }

    public HashMap<String, Object> saveDataOrderHeader(String idProduct, int qty) {   
        // diagram 7
        
        Product product = Product.getProduct(idProduct);
        this.totalAmount += product.getPrice() * qty;

        // ini hanya mengubah totalAmount
        orderHeaderDA.saveDA(idOrder, this.totalAmount); 

        // membuat order detail baru
        OrderDetail.saveOrderDetail(this.idOrder, idProduct, qty);

        HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
        returnHashMap.put("idOrder", this.idOrder);
        returnHashMap.put("idCustomer", this.idCustomer);
        returnHashMap.put("idPromo", this.idPromo);
        returnHashMap.put("orderedAt", this.orderedAt);
        returnHashMap.put("totalAmount", this.totalAmount);
        return returnHashMap;
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
