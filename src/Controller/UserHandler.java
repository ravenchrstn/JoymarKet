package Controller;

import Models.Customer;
import Validators.UserValidators;

public class UserHandler {
    private UserValidators userValidators;

    public UserHandler(UserValidators userValidators) {
        this.userValidators = userValidators;
    }
    
    public String registerCourier(String fullName, String email, String password, String confirm_password, String phone, String address) {
        // register account, diagram 1
        
        // validate input field
        String errorMessage = this.userValidators.validateRegister(fullName, email, password, confirm_password, phone, address);
        if (errorMessage != null) {
            return errorMessage;
        }

        String id = Customer.register(fullName, email, password, phone, address);
        if (id == null) {
            return "Your account has not been registered.";
        }

        return "Your account has successfully been registered!";
    }
}
