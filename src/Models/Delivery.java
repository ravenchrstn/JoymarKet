package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import DAs.DeliveryDA;

public class Delivery {
    private String idOrder, idUser, status;
    private static DeliveryDA deliveryDA = DeliveryDA.getDeliveryDA();

    public Delivery(String idOrder, String idUser, String status) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.status = status;
    }

    public static Delivery fromResultSet(ResultSet rs) {
        try {
            return new Delivery(rs.getString("idOrder"), rs.getString("idUser"), rs.getString("status"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String processDelivery(String idOrder, String idUser) {
        // diagram 12 - assign order to courier
        String status = deliveryDA.findStatus(idOrder, idUser);
        if (status.equals("pending")) return deliveryDA.updateStatus(idOrder, idUser, "in progress");
        else if (status.equals("in progress")) return deliveryDA.updateStatus(idOrder, idUser, "delivered");
        return null;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getStatus() {
        return status;
    }

}
