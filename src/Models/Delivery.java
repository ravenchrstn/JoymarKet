package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import DAs.DeliveryDA;
import Helpers.Result;

public class Delivery {
    private String idOrder, idCourier, status;
    private static DeliveryDA deliveryDA = DeliveryDA.getDeliveryDA();

    public Delivery(String idOrder, String idCourier, String status) {
        this.idOrder = idOrder;
        this.idCourier = idCourier;
        this.status = status;
    }

    public static Delivery fromResultSet(ResultSet rs) {
        try {
            return new Delivery(rs.getString("idOrder"), rs.getString("idCourier"), rs.getString("status"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Delivery createDelivery(String idOrder, String idCourier) {
        // diagram 12
        Result saveDA = deliveryDA.saveDA(idOrder, idCourier);
        return fromResultSet(saveDA.getRs());
    }

    public Delivery editDeliveryStatus(String idOrder, String status) {
        // diagram 14
        deliveryDA.saveStatusDA(idOrder, status);
        this.status = status;

        return this;
    }

    public Delivery getDelivery(String idOrder, String idCourier) {
        return fromResultSet(deliveryDA.read(idOrder, idCourier).getRs());
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdCourier() {
        return idCourier;
    }

    public String getStatus() {
        return status;
    }

}
