package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import da.DeliveryDA;
import exception.NoRowsAffectedException;

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

    public static String getStatus(String idOrder, String idUser) {
        return deliveryDA.findStatus(idOrder, idUser);
    }

    public static void updateStatus(String idOrder, String idUser, String status) throws NoRowsAffectedException, SQLException {
        deliveryDA.updateStatus(idOrder, idUser, status);
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
