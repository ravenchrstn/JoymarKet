package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Helpers.Checking;


public class Customer extends User {
    private Double balance;

    public Customer(String idUser, String fullName, String email, String password, String phone, String address, Double balance) {
        super(idUser, fullName, email, password, phone, address, "Customer");

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

    public static String registerCustomer(String fullName, String email, String password, String confirm_password, String phone, String address) { 
        // diagram 1 - register account

        // validate fullName, checking cannot by empty
        if (fullName == null || fullName.isEmpty()) return "Fullname field is empty. Try input something.";

        // validate email, checking must be filled, must end with @gmail.com
        if (email == null || email.isEmpty()) return "Email field is empty. Try input something.";
        if (email.endsWith("@gmail.com")) return "Inputted email must end with '@gmail.com'.";

        // validate password
        if (password.length() < 6) return "Inputted password must be at least 6 letters long.";

        //validate confirm_password
        if (password.equals(confirm_password) == false) return "Password and Confirm Password must have same value.";

        // validate phone
        if (phone == null || phone.isEmpty()) return "Phone field is empty. Try input something.";
        if (phone.length() >= 10 && phone.length() <= 13) return "Phone should be 10 to 13 digits long.";
        if (Checking.isNumeric(phone) == false) return "Phone must only contain numeric value.";

        // validate address
        if (address == null || address.isEmpty()) return "Address field is empty. Try input something.";

        Integer affectedRows = userDA.insertCustomer(fullName, email, password, phone, address);
        if (affectedRows <= 0) return "Registration failed. Please try again.";
        return null;
    }

    public int editProfile(String fullName, String email, String phone, String address) {
        return 0;
        // return userDA.saveDA(this.idUser, fullName, phone, address);
    }

    public static Customer getCustomer(String idUser) { // HERE
        return (Customer) userDA.read(idUser);
    }

    // public static ArrayList<Customer> getCustomer(ArrayList<String> idsCustomer) {
    //     ArrayList<User> users = userDA.read(idsCustomer);
    //     return users.stream()
    //             .filter(u -> u instanceof Customer)
    //             .map(u -> (Customer) u)
    //             .collect(Collectors.toCollection(ArrayList::new));
    // }

    public boolean topUpBalance(Double amount) {
        
        return false;
    }

    public HashMap<String, Object> createOrderHeader() {
        // Diagram 7
        OrderHeader oh = OrderHeader.createOrderHeader();
        HashMap<String, Object> hs = new HashMap<String, Object>();
        hs.put("OrderHeader", oh);
        hs.put("idUser", this.idUser);

        return hs;
    }

    public HashMap<String, Object> createCartItem() {
        // diagram 3
        HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
        CartItem cartItem = CartItem.createCartItem();
        returnHashMap.put("CartItem", cartItem);
        returnHashMap.put("idUser", this.idUser);

        return returnHashMap;
    }

    public Double getBalance() {
        return balance;
    }

}