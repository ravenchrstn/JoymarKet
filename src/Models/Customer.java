package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Customer extends User {
    private Double balance;

    public Customer(String idCustomer, String fullName, String email, String password, String phone, String address, Double balance) {
        super(idCustomer, fullName, email, password, phone, address, "Customer");

        if (balance != 0) this.balance = balance;
        else this.balance = 0.0;
    }

    public static Customer fromResultSet(ResultSet rs) {
        try {
            return new Customer(rs.getString("idUser"), rs.getString("fullName"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getString("address"), rs.getDouble("balance"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // public Customer registerAccount(String fullName, String email, String password, String phone, String address) { // HERE
    //     Customer customer = new Customer(null, fullName, email, password, phone, address, 0);

    //     this.saveDataUser(fullName, email, password, phone, address);
    //     return customer;
    // }

    public int editProfile(String fullName, String email, String phone, String address) { // HERE
        return userDA.saveDA(this.idUser, fullName, phone, address);
    }

    public static Customer getCustomer(String idCustomer) { // HERE
        return (Customer) userDA.read(idCustomer);
    }

    // public static ArrayList<Customer> getCustomer(ArrayList<String> idsCustomer) {
    //     ArrayList<User> users = userDA.read(idsCustomer);
    //     return users.stream()
    //             .filter(u -> u instanceof Customer)
    //             .map(u -> (Customer) u)
    //             .collect(Collectors.toCollection(ArrayList::new));
    // }

    public boolean topUpBalance(Double amount) { // HERE
        if (balance < 10000) {
            return false;
        }

        this.balance += amount;
        return userDA.topUpBalance(this.idUser, this.balance);
    }

    public static HashMap<String, String> saveDataUser(String fullName, String email, String password, String phone, String address) { 
        // registerAccount di class diagram, diagram 1

        HashMap<String, Object> hashMap = userDA.saveDA(fullName, email, password, phone, address);
        if ((int) hashMap.get("rowsAffected") < 1) return null; // kalau null, maka akun tidak terbuat

        String idUser = ((String) hashMap.get("generatedKeys"));

        HashMap<String, String> returnHashMap = new HashMap<String, String>();
        returnHashMap.put("idUser", idUser);
        returnHashMap.put("fullName", fullName);
        returnHashMap.put("email", email);
        returnHashMap.put("phone", phone);
        returnHashMap.put("address", address);

        return returnHashMap;
    }

    public HashMap<String, Object> createOrderHeader(String idPromo) {
        // Diagram 7
        // ambil dari cart kayanya ke order header
        return OrderHeader.createOrderHeader(this.idUser, idPromo);
    }

    public Double getBalance() {
        return balance;
    }

}