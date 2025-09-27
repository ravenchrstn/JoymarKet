package Models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.OrderHeaderDA;

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

    public static ArrayList<OrderHeader> findAssignedDeliveries(String idCourier) {
        // diagram 13 - view assigned deliveries

        // validate
        Courier courier = Courier.getCourier(idCourier);
        if (courier.getIdUser() == null) throw new IllegalArgumentException("courier not found.");
        else if (courier.getRole().equals("courier") == false) throw new IllegalArgumentException("not a courier.");
        return orderHeaderDA.findAssignedDeliveries(idCourier);

    }

    public static boolean doesExist(String idOrder) {
        // diagram 12 - business validators
        return orderHeaderDA.existsById(idOrder);
    }

    public static ArrayList<HashMap<String, Object>> findCustomerOrderHistories(String idCustomer) {
        // diagram 8 - view order history
        return orderHeaderDA.findCustomerOrderHistories(idCustomer);
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

    public static ArrayList<HashMap<String, Object>> findAllOrders() {
        // diagram 11 - view all orders
        return orderHeaderDA.findAllOrders();
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
