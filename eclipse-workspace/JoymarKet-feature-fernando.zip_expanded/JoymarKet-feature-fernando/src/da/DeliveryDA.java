package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.Connect;
import exception.NoRowsAffectedException;
import model.Delivery;

public class DeliveryDA {

    private static DeliveryDA deliveryDA;
    private Connect connect = Connect.getInstance();

    public static DeliveryDA getDeliveryDA() {
        if (deliveryDA == null) deliveryDA = new DeliveryDA();
        return deliveryDA;
    }

    //  DIAGRAM 13 - View Assigned Deliveries (Courier)
    public String findStatus(String idOrder, String idUser) {
        String query = String.format(
            "SELECT status FROM delivery WHERE idOrder = '%s' AND idUser = '%s';",
            idOrder, idUser
        );
        try {
            ResultSet rs = this.connect.execQuery(query);
            if (!rs.next()) return null;
            return rs.getString("status");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Delivery> getAssignedDeliveries(String idUser) throws SQLException {
        String query = String.format(
            "SELECT * FROM delivery WHERE idUser = '%s';",
            idUser
        );

        List<Delivery> list = new ArrayList<>();
        ResultSet rs = this.connect.execQuery(query);

        while (rs.next()) {
            list.add(Delivery.fromResultSet(rs));
        }
        return list;
    }

    //  DIAGRAM 14 - Update Delivery Status (Courier)
    public void updateStatus(String idOrder, String idUser, String status)
            throws NoRowsAffectedException, SQLException {

        String query = String.format(
            "UPDATE delivery SET status = '%s' WHERE idOrder = '%s' AND idUser = '%s';",
            status, idOrder, idUser
        );

        HashMap<String, Object> hm = this.connect.execUpdate(query);

        int affected = (Integer) hm.get("rowsAffected");
        if (affected <= 0) {
            throw new NoRowsAffectedException(
                "Failed to update delivery status. The order may not exist or is not assigned to you."
            );
        }
    }

    //  DIAGRAM 12
    public void assignOrder(String idOrder, String idCourier) throws SQLException, NoRowsAffectedException {
        String query = String.format(
            "INSERT INTO delivery (idOrder, idUser, status) VALUES ('%s', '%s', 'pending');",
            idOrder, idCourier
        );
        HashMap<String, Object> hm = this.connect.execUpdate(query);
        if ((Integer) hm.get("rowsAffected") <= 0) {
            throw new NoRowsAffectedException("Failed to assign order.");
        }
    }

}
