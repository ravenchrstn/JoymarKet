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

    public String findStatus(String idOrder, String idCourier) {
    	// diagram 12: assign order to courier
        String query = "SELECT status FROM delivery WHERE idOrder = '" + idOrder +
                       "' AND idCourier = '" + idCourier + "';";
        try {
            ResultSet rs = connect.execQuery(query);
            return rs.next() ? rs.getString("status") : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateStatus(String idOrder, String idCourier, String status) throws NoRowsAffectedException, SQLException {

    	 if (status.equalsIgnoreCase("completed")) {

    	        String deleteQuery =
    	            "DELETE FROM delivery WHERE idOrder = '" + idOrder +
    	            "' AND idCourier = '" + idCourier + "'";

    	        HashMap<String, Object> hm = connect.execUpdate(deleteQuery);

    	        if ((Integer) hm.get("rowsAffected") <= 0)
    	            throw new NoRowsAffectedException("Failed to delete completed delivery.");

    	        return;
    	    }
    	    String query =
    	        "UPDATE delivery SET status = '" + status +
    	        "' WHERE idOrder = '" + idOrder +
    	        "' AND idCourier = '" + idCourier + "'";

    	    HashMap<String, Object> hm = connect.execUpdate(query);

    	    if ((Integer) hm.get("rowsAffected") <= 0)
    	        throw new NoRowsAffectedException("Failed to update status. No matching delivery found.");
    }
    
    // update delivery
    public void save(String idOrder, String idCourier, String status)
            throws SQLException, NoRowsAffectedException {

        // Check if delivery row exists
        String checkQuery = "SELECT COUNT(*) AS cnt FROM delivery WHERE idOrder = '" + idOrder + "'";
        ResultSet rs = connect.execQuery(checkQuery);
        rs.next();
        int count = rs.getInt("cnt");

        if (count > 0) {
            // update
            String updateQuery =
                "UPDATE delivery SET idCourier = '" + idCourier + "', status = '" + status + 
                "' WHERE idOrder = '" + idOrder + "'";
            HashMap<String, Object> hm = connect.execUpdate(updateQuery);
            if ((Integer) hm.get("rowsAffected") <= 0)
                throw new NoRowsAffectedException("Failed to update delivery.");
        }
        else {
            // insert
            String insertQuery =
                "INSERT INTO delivery (idOrder, idCourier, status) VALUES ('" +
                idOrder + "', '" + idCourier + "', '" + status + "')";
            HashMap<String, Object> hm = connect.execUpdate(insertQuery);
            if ((Integer) hm.get("rowsAffected") <= 0)
                throw new NoRowsAffectedException("Failed to insert delivery.");
        }
    }
}
