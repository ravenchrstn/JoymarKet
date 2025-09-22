package Queries;

import java.util.ArrayList;

public abstract class CustomerQueries {

    public static String generateReadQuery(String idCustomer) {
        return "SELECT idUser, fullName, email, password, phone, address, role, balance FROM users WHERE idUser = " + idCustomer + " AND role = 'customer'" + " LIMIT 1";
    }

    public static String generateReadQuery(ArrayList<String> idsCustomer) {
        String query = "SELECT idUser, fullName, email, password, phone, address, role, balance FROM users WHERE role = 'customer' AND idUser in (";
        for (int i = 0; i < idsCustomer.size(); i++) {
            query += idsCustomer.get(i) + ", ";
        }
        query += ")";

        return query;
    }

    // public static String generateRegisterQuery(Customer customer) {
    //     return "INSERT INTO users (fullName, email, password, phone, address, role, balance) VALUES (" + customer.fullName  + ", " + customer.email + ", " + customer.password + ", " + customer.phone + ", " + customer.address + ", " + customer.balance + ")";
    // }

    // public static String generateUpdateQuery(Customer customer) {
    //     return "ALTER users SET fullName = " + customer.fullName + ", phone = " + customer.phone + ", address = " + customer.address + ", balance = " + customer.balance + " WHERE idUser = " + customer.idUser + " AND role = 'customer'";
    // }
}