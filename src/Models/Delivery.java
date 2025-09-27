package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import DAs.DeliveryDA;

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

    public static String updateStatus(String idOrder, String idCourier) {
        // diagram 12 - assign order to courier

        // validation
        Courier courier = Courier.getCourier(idCourier);
        if (courier.getIdUser() == null) throw new IllegalArgumentException("courier not found.");
        else if (courier.getRole().equals("courier") == false) throw new IllegalArgumentException("not a courier.");

        if (OrderHeader.doesExist(idOrder) == false) throw new IllegalArgumentException("order does not exist.");

        String status = deliveryDA.findStatus(idOrder, idCourier);
        if (status.equals("pending")) return deliveryDA.updateStatus(idOrder, idCourier, "in progress");
        else if (status.equals("in progress")) return deliveryDA.updateStatus(idOrder, idCourier, "delivered");
        return null;
    }

    public Delivery editDeliveryStatus(String idOrder, String status) {
        // diagram 14
        
        return this;
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
