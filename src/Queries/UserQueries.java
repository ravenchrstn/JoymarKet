package Queries;

import java.util.ArrayList;

import Models.User;

public abstract class UserQueries {
    public static String generateUpdateQuery(User user) {
        return "UPDATE users SET fullName = " + user.getFullName() + ", phone = " + user.getPhone() + ", address = " + user.getAddress() + " WHERE idUser = " + user.getIdUser();
    }

    public static String generateUpdateQuery(String idUser, String fullName, String phone, String address) {
        return "UPDATE users SET fullName = " + fullName + ", phone = " + phone + ", address = " + address + " WHERE idUser = " + idUser;
    }

    public static String generateUpdateQuery(String idCustomer, Double newBalance) {
        return "UPDATE users SET balance = " + newBalance + " WHERE idUser = " + idCustomer;
    }

    public static String generateReadQuery(String idUser) {
        return "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE idUser = " + idUser + " LIMIT 1";
    }

    public static String generateReadCustomerQuery(String idUser) {
        return "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'customer' AND idUser = " + idUser + " LIMIT 1";
    }

    public static String generateReadCourierQuery(String idUser) {
        return "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'courier' AND idUser = " + idUser + " LIMIT 1";
    }
    public static String generateReadAdminQuery(String idUser) {
        return "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'admin' AND idUser = " + idUser + " LIMIT 1";
    }

    public static String generateReadQuery(ArrayList<String> idsUser) {
        String query = "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE idUser in (";
        for (int i = 0; i < idsUser.size(); i++) {
            query += idsUser.get(i) + ", ";
        }
        query += ")";
        return query;
    }

    public static String generateReadCustomerQuery(ArrayList<String> idsUser) {
        String query = "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'customer' idUser in (";
        for (int i = 0; i < idsUser.size(); i++) {
            query += idsUser.get(i) + ", ";
        }
        query += ")";
        return query;
    }

    public static String generateReadCourierQuery(ArrayList<String> idsUser) {
        String query = "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'courier' idUser in (";
        for (int i = 0; i < idsUser.size(); i++) {
            query += idsUser.get(i) + ", ";
        }
        query += ")";
        return query;
    }

    public static String generateReadAdminQuery(ArrayList<String> idsUser) {
        String query = "SELECT idUser, fullName, email, password, phone, address, role FROM users WHERE role = 'admin' idUser in (";
        for (int i = 0; i < idsUser.size(); i++) {
            query += idsUser.get(i) + ", ";
        }
        query += ")";
        return query;
    }
}
