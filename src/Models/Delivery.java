package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import DAs.DeliveryDA;
import Exceptions.NoRowsAffectedException;

public class Delivery {
    private String idOrder, idUser, status;
    private static DeliveryDA deliveryDA = DeliveryDA.getDeliveryDA();

    public Delivery(String idOrder, String idUser, String status) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.status = status;
    }

    public static Delivery fromResultSet(ResultSet rs) throws SQLException {
        return new Delivery(rs.getString("idOrder"), rs.getString("idUser"), rs.getString("status"));
    }

    public static void processDelivery(String idOrder, String idUser) throws NoRowsAffectedException, SQLException {
        // diagram 12 - assign order to courier
        String status = deliveryDA.findStatus(idOrder, idUser);
        if (status.equals("pending")) deliveryDA.updateStatus(idOrder, idUser, "in progress");
        else if (status.equals("in progress")) deliveryDA.updateStatus(idOrder, idUser, "delivered");
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
