package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Courier extends User {
    private String vehicleType, vehiclePlate;

    public Courier(String idCourier, String fullName, String email, String password, String phone, String address, String vehicleType, String vehiclePlate) {
        super(idCourier, fullName, email, password, phone, address, "Courier");
        this.vehicleType = vehicleType;
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public static Courier fromResultSet(ResultSet rs) {
        try {
            return new Courier(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getString("vehicleType"), rs.getString("vehiclePlate"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static Courier getCourier(String idCourier) { // HERE
        return (Courier) userDA.read(idCourier);
    }
}
