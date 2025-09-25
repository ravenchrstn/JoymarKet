package DAs;

import java.sql.SQLException;
import java.util.ArrayList;

import App.Connect;
import Helpers.Result;
import Models.OrderHeader;
import Queries.OrderHeaderQueries;

public class OrderHeaderDA {
    private Connect connect = Connect.getInstance();
    private static OrderHeaderDA orderHeaderDA;

    public static OrderHeaderDA getOrderHeaderDA() {
        if (orderHeaderDA == null) orderHeaderDA = new OrderHeaderDA();
        return orderHeaderDA;
    }

    public int saveDA(String idOrder, Double totalAmount) {
        // update totalAmount
        String query = OrderHeaderQueries.generateUpdateTotalAmountQuery(idOrder, totalAmount);
        return (int) connect.execUpdate(query).get("rowsAffected");
    }

    // public int saveDA(String idOrder, String idProduct, int qty) {
    //     // update total amount
    //     String query = OrderHeaderQueries.generateUpdateTotalAmountQuery(idOrder, idProduct, qty);
    //     return (int) connect.execUpdate(query).get("rowsAffected");
    // }

    public OrderHeader read(String idOrder) {
        String query = OrderHeaderQueries.generateReadQuery(idOrder);
        return OrderHeader.fromResultSet(connect.execQuery(query).getRs());
    }

    public ArrayList<OrderHeader> read() {
        String query = OrderHeaderQueries.generateReadQuery();
        Result res = connect.execQuery(query);
        ArrayList<OrderHeader> ohs = new ArrayList<OrderHeader>();

        try {
            while (res.getRs().next()) {
                OrderHeader oh = OrderHeader.fromResultSet(res.getRs());
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

    public int updateStatus(String idOrder, String status) {
        String query = OrderHeaderQueries.generateChangeStatusQuery(idOrder, status);
        return (int) connect.execUpdate(query).get("rowsAffected");
    }
}
