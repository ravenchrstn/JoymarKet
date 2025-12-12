package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import app.Connect;
import da.OrderHeaderDA;
import exception.NoRowsAffectedException;

public class OrderHeader {

    private String idOrder;
    private String idCustomer;
    private String idPromo;
    private String status;
    private String deliveryStatus;
    private Date orderedAt;
    private Double totalAmount;

    private static final OrderHeaderDA orderHeaderDA = OrderHeaderDA.getOrderHeaderDA();

    public OrderHeader(String idOrder, String idCustomer, String idPromo,
                       String status, String deliveryStatus, Date orderedAt, Double totalAmount) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idPromo = idPromo;
        this.status = status;
        this.deliveryStatus = deliveryStatus;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
    }

    public static OrderHeader fromResultSet(ResultSet rs) throws SQLException {

        String idOrder = rs.getString("idOrder");
        String idCustomer = rs.getString("idCustomer");
        String idPromo = rs.getString("idPromo");
        String status = rs.getString("status");
        Date orderedAt = rs.getDate("orderedAt");
        Double totalAmount = rs.getDouble("totalAmount");

        // deliveryStatus defaultnya pending, sampai di edit sm kurir
        String deliveryStatus = "pending";

        try {
            String temp = rs.getString("deliveryStatus");
            if (temp != null) deliveryStatus = temp;
        } catch (SQLException ignore) {
            // Query did not contain deliveryStatus column → keep "pending"
        }
        
        return new OrderHeader(
        	idOrder, idCustomer, idPromo, status, deliveryStatus, orderedAt, totalAmount
        );
    }
    
    public static ArrayList<OrderHeader> getAssignedDeliveries(String idUser) throws SQLException {
        // diagram 13 - view assigned deliveries
        return orderHeaderDA.findAssignedDeliveriesByIdUser(idUser);
    }
    
    public static ArrayList<OrderHeader> getUnassignedOrders() throws SQLException {

        String query =
        	"SELECT oh.* FROM orderheader oh " +
        	"LEFT JOIN delivery d ON oh.idOrder = d.idOrder " +
        	"WHERE (d.idOrder IS NULL OR d.status = 'pending') " +
        	"AND oh.status NOT IN ('cancelled','completed')";

        ResultSet rs = Connect.getInstance().execQuery(query);
        ArrayList<OrderHeader> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new OrderHeader(
                rs.getString("idOrder"),
                rs.getString("idCustomer"),
                rs.getString("idPromo"),
                rs.getString("status"),
                rs.getString("deliveryStatus"),
                rs.getDate("orderedAt"),
                rs.getDouble("totalAmount")
            ));
        }

        return list;
    }

    public static ArrayList<HashMap<String, Object>> getCustomerOrderHistories(String idUser) throws SQLException {
        // diagram 8 - view order history
        return orderHeaderDA.findOrderHistoriesByIdUser(idUser);
    }

    public static ArrayList<HashMap<String, Object>> getAllOrders() throws SQLException {
        // diagram 11 - view all orders
        return orderHeaderDA.findAllOrders();
    }

    public static String insert(String idUser, String idPromo, Double totalAmount) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        return orderHeaderDA.insert(idUser, idPromo, totalAmount);
    }
    
    public static void markAsAssigned(String idOrder) throws SQLException, NoRowsAffectedException {
        String query = "UPDATE orderheader SET assigned = 1 WHERE idOrder = '" + idOrder + "';";
        HashMap<String,Object> hm = Connect.getInstance().execUpdate(query);

        if ((Integer) hm.get("rowsAffected") <= 0) {
            throw new NoRowsAffectedException("Failed to update order assignment status.");
        }
    }
    
    public String getAssignedCourier() {
        try {
            return OrderHeaderDA.getOrderHeaderDA().findAssignedCourier(idOrder);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static ArrayList<OrderHeader> getCompletedDeliveries(String idCourier) throws SQLException {
        return orderHeaderDA.findCompletedDeliveriesByCourier(idCourier);
    }
    
    public static String cancelOrder(String orderId) throws SQLException, NoRowsAffectedException {

        // 1. get totalAmount + idCustomer
        String query = 
            "SELECT idCustomer, totalAmount FROM orderheader WHERE idOrder = '" + orderId + "'";
        ResultSet rs = Connect.getInstance().execQuery(query);

        if (!rs.next()) return "Order not found.";

        String customerId = rs.getString("idCustomer");
        double amount = rs.getDouble("totalAmount");

        // 2. refund money
        String refundQuery =
        	"UPDATE customer SET balance = balance + " + amount + 
        	" WHERE idCustomer = '" + customerId + "'";
        Connect.getInstance().execUpdate(refundQuery); 

        // 3. update orderheader(status)
        String q1 = 
            "UPDATE orderheader SET status = 'cancelled' WHERE idOrder = '" + orderId + "'";
        Connect.getInstance().execUpdate(q1);

        // 4. update delivery(status)
        String q2 = 
            "UPDATE delivery SET status = 'cancelled' WHERE idOrder = '" + orderId + "'";
        Connect.getInstance().execUpdate(q2);

        return "Order cancelled successfully!";
    }
    
    public static ArrayList<OrderHeader> getAssignedOrders() throws SQLException {
        return orderHeaderDA.findAssignedOrders();
    }
    
    public static ArrayList<OrderHeader> getCompletedOrders() throws SQLException {
        return orderHeaderDA.findCompletedOrders();
    }
    
    public static ArrayList<OrderHeader> getUnnotifiedCancelledOrders(String idUser) throws SQLException {
        return orderHeaderDA.findUnnotifiedCancelledOrders(idUser);
    }
    
    public static ArrayList<OrderHeader> getUnnotifiedCompletedOrders(String idUser) throws SQLException {
        return orderHeaderDA.findUnnotifiedCompletedOrders(idUser);
    }

    public static void markCustomerNotified(String idOrder) throws SQLException {
        orderHeaderDA.updateCustomerNotified(idOrder);
    }
    
    public static void markCustomerNotifiedCompleted(String idOrder) throws SQLException {
        String query = "UPDATE orderheader SET notifiedCompleted = 1 WHERE idOrder = '" + idOrder + "'";
        Connect.getInstance().execUpdate(query);
    }
    
    public static ArrayList<OrderHeader> getOngoingOrders(String customerId) throws SQLException {
        return orderHeaderDA.findOngoingOrdersByCustomer(customerId);
    }

    public static ArrayList<OrderHeader> getCompletedOrdersByCustomer(String customerId) throws SQLException {
        return orderHeaderDA.findCompletedOrdersByCustomer(customerId);
    }

    public static ArrayList<OrderHeader> getCancelledOrders(String customerId) throws SQLException {
        return orderHeaderDA.findCancelledOrdersByCustomer(customerId);
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getCustomer() {
    	return idCustomer;
    }
    
    public String getIdPromo() {
        return idPromo;
    }

    public String getStatus() {
        return status;
    }
    
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public static OrderHeaderDA getOrderHeaderDA() {
        return orderHeaderDA;
    }
}
