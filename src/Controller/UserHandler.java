package Controller;

import java.util.ArrayList;

import Models.Courier;
import Models.Customer;

public class UserHandler {

    public ArrayList<Courier> getAllCouriers() {
        // diagram 10 - view all couriers
        return Courier.getAllCouriers();
    }
    
    public String registerCustomer(String fullName, String email, String password, String confirm_password, String phone, String address) {
        // diagram 1 - register account

        String id = Customer.createCustomer(fullName, email, password, confirm_password, phone, address);
        if (id == null) {
            return "Registration failed. Please try again.";
        }

        return "Your account has successfully been registered!";
    }
}
