package Validators;

import Helpers.Checking;

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
}
