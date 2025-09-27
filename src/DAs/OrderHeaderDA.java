package DAs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import App.Connect;
import Models.OrderHeader;
import Queries.OrderHeaderQueries;

public class OrderHeaderDA {
    private Connect connect = Connect.getInstance();
    private static OrderHeaderDA orderHeaderDA;

    public static OrderHeaderDA getOrderHeaderDA() {
        if (orderHeaderDA == null) orderHeaderDA = new OrderHeaderDA();
        return orderHeaderDA;
    }

    public ArrayList<HashMap<String, Object>> findCustomerOrderHistories(String idCustomer) {
        // diagram 8 - view order history
        String query = "SELECT oh.idOrder, oh.orderedAt, oh.totalAmount, od.qty, p.idProduct, p.name FROM order_headers oh JOIN order_details od ON oh.idOrder = od.idOrder JOIN products p ON od.idProduct = p.idProduct JOIN (SELECT idOrder, MIN(idProduct) FROM order_details GROUP BY idOrder) od_single ON od.idOrder = od_single.idOrder AND od_single.idProduct = od.idProduct WHERE oh.idCustomer = " + idCustomer + " ORDER BY oh.orderedAt DESC;";
        ResultSet rs = connect.execQuery(query).getRs();
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

        try {
            while (rs.next()) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("idOrder", rs.getString("idOrder"));
                hm.put("idProduct", rs.getString("idProduct"));
                hm.put("name", rs.getString("name"));
                hm.put("orderedAt", rs.getString("orderedAt"));
                hm.put("totalAmount", rs.getString("totalAmount"));
                hm.put("qty", rs.getString("qty"));
                al.add(hm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public ArrayList<HashMap<String, Object>> findAllOrders() {
        // diagram 11 - view all orders
        String query = "SELECT oh.idOrder, oh.orderedAt, oh.totalAmount, oh.status, od.qty, p.idProduct, p.name FROM order_headers oh JOIN order_details od ON oh.idOrder = od.idOrder JOIN products p ON od.idProduct = p.idProduct JOIN (SELECT idOrder, MIN(idProduct) FROM order_details GROUP BY idOrder) od_single ON od.idOrder = od_single.idOrder AND od_single.idProduct = od.idProduct ORDER BY oh.orderedAt DESC;";
        ResultSet rs = connect.execQuery(query).getRs();
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

        try {
            while (rs.next()) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("idOrder", rs.getString("idOrder"));
                hm.put("idProduct", rs.getString("idProduct"));
                hm.put("name", rs.getString("name"));
                hm.put("status", rs.getString("status"));
                hm.put("orderedAt", rs.getString("orderedAt"));
                hm.put("totalAmount", rs.getString("totalAmount"));
                hm.put("qty", rs.getString("qty"));
                al.add(hm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return al;
    }

    // public int saveDA(String idOrder, String idProduct, int qty) {
    //     // update total amount
    //     String query = OrderHeaderQueries.generateUpdateTotalAmountQuery(idOrder, idProduct, qty);
    //     return (int) connect.execUpdate(query).get("rowsAffected");
    // }

    public boolean existsById(String idOrder) {
        // diagram 12 - business validators
        String query = "SELECT idOrder FROM order_headers";
        ResultSet rs = connect.execQuery(query).getRs();
        
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<OrderHeader> findAssignedDeliveries(String idCourier) {
        String query = "SELECT idOrder, idCustomer, status, orderedAt, totalAmount FROM order_headers oh JOIN deliveries d ON oh.idOrder = d.idOrder WHERE d.idCourier = " + idCourier + ";";
        ResultSet rs = connect.execQuery(query).getRs();
        ArrayList<OrderHeader> ohs = new ArrayList<OrderHeader>();
        try {
            while (rs.next()) {
                OrderHeader oh = OrderHeader.fromResultSet(rs);
                ohs.add(oh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ohs;
    }

    public Double readTotalAmount(String idOrder) {
        String query = OrderHeaderQueries.generateReadTotalAmountQuery(idOrder);
        try {
            return connect.execQuery(query).getRs().getDouble("total_amount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
