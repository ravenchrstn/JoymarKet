package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public static HashMap<String, String> getCourier(String idCourier) {
        // diagram 10, kemungkinan tidak dipakai
        User user = userDA.read(idCourier);
        HashMap<String, String> returnHashMap = new HashMap<String, String>();
        returnHashMap.put("idUser", user.getIdUser());
        returnHashMap.put("name", user.getFullName());
        returnHashMap.put("email", user.getEmail());
        returnHashMap.put("phone", user.getEmail());
        returnHashMap.put("address", user.getAddress());
        
        return returnHashMap;
    }

    public static ArrayList<Courier> getAllCouriers() {
        // diagram 10 - view all couriers
        ArrayList<Courier> couriers = userDA.getAllCouriers(); 
        return couriers;
    }
}
