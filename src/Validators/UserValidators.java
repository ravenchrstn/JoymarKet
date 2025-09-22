package Validators;

import Helper.Checking;

public class UserValidators {

    public boolean validateFullName(String fullName) {
        return !(fullName == null || fullName.isEmpty());
    }

    public boolean validatePhone(String phone) {
        return !(phone == null || phone.isEmpty() || (phone.length() >= 10 && phone.length() <= 13)|| Checking.isNumeric(phone) == false);
    }

    public boolean validateAddress(String address) {
        return !(address == null || address.isEmpty());
    }
    
    public boolean validateBalanceAmount(Double amount) {
        if (amount == null) return false;
        return true;
    }

    public boolean validateDeliveryStatus(String status) {
        if (status.equals("Pending") || status.equals("In Progress") || status.equals("Delivered")) return true;
        return false;
    }

    public boolean validateLogin(String email, String password) {
        if (email == null) return false;
        if (password == null) return false;
        return true;
    }

    public String validateRegister(String fullName, String email, String password, String confirm_password, String phone, String address) {
        // validate fullName, checking cannot by empty
        if (this.validateFullName(fullName) == false) return "Fullname field is empty. Try input something.";

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
        if (this.validateAddress(address) == false) return "Address field is empty. Try input something.";

        return null;
    }
}
