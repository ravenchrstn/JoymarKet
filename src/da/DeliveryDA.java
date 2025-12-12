package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;

public class DeliveryDA {
    private static DeliveryDA deliveryDA;
    private Connect connect = Connect.getInstance();

    public static DeliveryDA getDeliveryDA() {
        if (deliveryDA == null) deliveryDA = new DeliveryDA();
        return deliveryDA;
    }

    public String findStatus(String idOrder, String idUser) {
        // diagram 12 - assign order to courier
        String query = "SELECT status FROM delivery WHERE idOrder = " + idOrder + " AND idUser = " + idUser + ";";
        try {
            return this.connect.execQuery(query).getString("status");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateStatus(String idOrder, String idUser, String status) throws NoRowsAffectedException, SQLException {
        // diagram 12 - assign order to courier
        String query = "UPDATE delivery SET status = " + status + " WHERE idUser = " + idUser + " AND idUser = " + idUser + ";";
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        ResultSet rs = (ResultSet) hm.get("resultSet");
        rs.next();
        if ((Integer) hm.get("rowsAffected") <= 0) throw new NoRowsAffectedException("Failed to update status. The order may not exist.");
    }
}
