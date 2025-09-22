package DAs;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public int saveDA(String idOrder, String idProduct, int qty) {
        // update total amount
        String query = OrderHeaderQueries.generateUpdateTotalAmountQuery(idOrder, idProduct, qty);
        return (int) connect.execUpdate(query).get("rowsAffected");
    }

    public ArrayList<OrderHeader> read(String query) {
        throw new UnsupportedOperationException("Unimplemented method 'read'");
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

    public <R extends OrderHeader> int saveDA(R orderHeader, String query) {
        throw new UnsupportedOperationException("Unimplemented method 'saveDA'");
    }

    public <R extends OrderHeader> int saveDA(ArrayList<R> orderHeaders, String query) {
        throw new UnsupportedOperationException("Unimplemented method 'saveDA'");
    }

    public int updateStatus(String idOrder, String status) {
        String query = OrderHeaderQueries.generateChangeStatusQuery(idOrder, status);
        return (int) connect.execUpdate(query).get("rowsAffected");
    }
}
