package Models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.OrderHeaderDA;

public class OrderHeader {
    private String idOrder, idUser, idPromo, status;
    private Date orderedAt;
    private Double totalAmount;
    private static final OrderHeaderDA orderHeaderDA = OrderHeaderDA.getOrderHeaderDA();

    public OrderHeader(String idOrder, String idUser, String idPromo, String status, Date orderedAt, Double totalAmount) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idPromo = idPromo;
        this.status = status;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
    }

    public static OrderHeader fromResultSet(ResultSet rs) {
        try {
            return new OrderHeader(rs.getString("idOrder"), rs.getString("idUser"), rs.getString("idPromo"), rs.getString("status"), rs.getDate("orderedAt"), rs.getDouble("totalAmount"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<OrderHeader> findAssignedDeliveries(String idUser) {
        // diagram 13 - view assigned deliveries
        return orderHeaderDA.findAssignedDeliveriesByIdUser(idUser);
    }

    public static ArrayList<HashMap<String, Object>> findCustomerOrderHistories(String idUser) {
        // diagram 8 - view order history
        return orderHeaderDA.findOrderHistoriesByIdUser(idUser);
    }

    public static HashMap<String, Object> createOrderHeader(String idUser, String idPromo) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("OrderHeader", new OrderHeader(null, idUser, idPromo, "Pending", null, 0.0));
        hashMap.put("idUser", idUser);
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

    public String getIdUser() {
        return idUser;
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
