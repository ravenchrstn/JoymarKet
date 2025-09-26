package Queries;

import java.util.ArrayList;

import Models.Courier;

public class CourierQueries {

    public static String generateReadQuery(ArrayList<String> idsCourier) {
        String query = "SELECT idUser, fullName, email, password, phone, address, role, vehicleType, vehiclePlate FROM users WHERE role = 'courier' AND idUser in (";
        for (int i = 0; i < idsCourier.size(); i++) {
            query += idsCourier.get(i) + ", ";
        }
        query += ")";
        return query;
    }

    public static String generateRegisterQuery(Courier courier) {
        return "INSERT INTO users (fullName, email, password, phone, address, role, vehicleType, vehiclePlate) VALUES (" + courier.getFullName()  + ", " + courier.getEmail() + ", " + courier.getPassword() + ", " + courier.getPhone() + ", " + courier.getAddress() + ", " + courier.getVehicleType() + ", " + courier.getVehiclePlate() + ")";
    }

    public static String generateUpdateQuery(Courier courier) {
        return "UPDATE users SET fullName = " + courier.getFullName() + ", phone = " + courier.getPhone() + ", address = " + courier.getAddress() + ", vehicleType = " + courier.getVehicleType() + ", vehiclePlate = " + courier.getVehiclePlate() + " WHERE role = 'courier' AND idUser = " + courier.getIdUser();
    }
}
