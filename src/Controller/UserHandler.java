package Controller;

import java.util.ArrayList;

import Models.Courier;
import Models.Customer;
import Models.User;

public class UserHandler {

    public ArrayList<Courier> findAllCouriers() {
        // diagram 10 - view all couriers
        return Courier.findAll();
    }
    
    public String registerCustomer(String fullName, String email, String password, String confirm_password, String phone, String address) {
        // diagram 1 - register account

        String errorMessage = Customer.registerCustomer(fullName, email, password, confirm_password, phone, address);
        return errorMessage;
    }

    public String login(String email, String password) {
        String errorMessage = User.login(email, password);
        return errorMessage;
    }
}
