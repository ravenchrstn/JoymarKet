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

    public static ArrayList<HashMap<String, Object>> getCouriers() {
        ArrayList<Courier> couriers = userDA.readCouriers();
        ArrayList<HashMap<String, Object>> returnCouriers = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < couriers.size(); i++) {
            HashMap<String, Object> hs = new HashMap<String, Object>();
            hs.put("idCourier", couriers.get(i).getIdUser());
            hs.put("name", couriers.get(i).getFullName());
            hs.put("email", couriers.get(i).getEmail());
            hs.put("phone", couriers.get(i).getPhone());
            hs.put("address", couriers.get(i).getAddress());
            hs.put("vehicleType", couriers.get(i).getVehicleType());
            hs.put("vehiclePlate", couriers.get(i).getVehiclePlate());
            returnCouriers.add(hs);
        }

        return returnCouriers;
    }
}
