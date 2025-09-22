package Controller;

import java.util.HashMap;

import Models.Customer;
import Validators.UserValidators;

public class UserHandler {
    private UserValidators userValidators;

    public UserHandler(UserValidators userValidators) {
        this.userValidators = userValidators;
    }
    
    public HashMap<String, String> saveDataUser(String fullName, String email, String password, String confirm_password, String phone, String address) {
        // register account, diagram 1
        
        // validate input field
        String errorMessage = this.userValidators.validateRegister(fullName, email, password, confirm_password, phone, address);
        if (errorMessage != null) {
            HashMap<String, String> userData = new HashMap<String, String>();
            userData.put("errorMessage", errorMessage);
            return userData;
        }

        HashMap<String,String> userData = Customer.saveDataUser(fullName, email, password, phone, address);
        if (userData == null) {
            userData = new HashMap<String, String>();
            userData.put("errorMessage", "Your account has not been registered.");
        }

        return userData;
    }
}
