package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Courier extends User {
    private String vehicleType, vehiclePlate;

    public Courier(String idUser, String fullName, String email, String password, String phone, String address, String vehicleType, String vehiclePlate) {
        super(idUser, fullName, email, password, phone, address, "Courier");
        this.vehicleType = vehicleType;
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public static Courier fromResultSet(ResultSet rs) throws SQLException {
        return new Courier(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getString("vehicleType"), rs.getString("vehiclePlate"));

    }

    public static ArrayList<Courier> getAll() throws SQLException {
        // diagram 10 - view all couriers
        return userDA.findAllCouriers();
    }
}
