package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;

public class OrderDetailDA {
    private static OrderDetailDA orderDetailDA;
    private Connect connect = Connect.getInstance();

    public static OrderDetailDA getOrderDetailDA() {
        if (orderDetailDA == null) orderDetailDA = new OrderDetailDA();
        return orderDetailDA;
    }

    public void insert(String idOrder, String idProduct, Integer qty) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        String query = "INSERT INTO order_details(idOrder, idProduct, qty) VALUES (" + idOrder + ", " + idProduct + ", " + qty + ");";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        rs.next();
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Failed to add order detail.");
    }
}
