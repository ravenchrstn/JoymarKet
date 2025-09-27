package DAs;

import java.sql.ResultSet;
import java.sql.SQLException;
import App.Connect;

public class DeliveryDA {
    private static DeliveryDA deliveryDA;
    private Connect connection = Connect.getInstance();

    public static DeliveryDA getDeliveryDA() {
        if (deliveryDA == null) deliveryDA = new DeliveryDA();
        return deliveryDA;
    }

    public String findStatus(String idOrder, String idCourier) {
        String query = "SELECT status FROM deliveries WHERE idOrder = " + idOrder + " AND idCourier = " + idCourier + ";";
        String status = null;
        try {
            status = this.connection.execQuery(query).getRs().getString("status");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    public String updateStatus(String idOrder, String idCourier, String status) {
        // diagram 12 - assign order to courier
        String query = "INSERT INTO deliveries (idOrder, idCourier, status) VALUES ('" + idOrder + "', '" + idCourier + "', '" + status + "');";
        ResultSet rs = connection.execQuery(query).getRs();
        String statusReturn = null;

        try {
            statusReturn = rs.getString(status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusReturn;
    }
}
