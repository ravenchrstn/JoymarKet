package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.InvalidInputException;
import Exceptions.NoRowsAffectedException;
import Exceptions.NotFoundException;
import Helpers.Checking;
import Models.Courier;
import Models.Customer;
import Models.User;
import Responses.MultipleObjectsResponse;

public class UserHandler {

    public MultipleObjectsResponse getAllCouriers() {
        // diagram 10 - view all couriers
        try {
            ArrayList<Courier> couriers = Courier.getAll();
            if (couriers.isEmpty() == true) return new MultipleObjectsResponse<>("There are no registered couriers.", couriers);
            return new MultipleObjectsResponse("", couriers);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MultipleObjectsResponse<>("An error occured while processing your request. Please try again later.", new ArrayList<>());
        }
    }
    
    public String registerCustomer(String fullName, String email, String password, String confirm_password, String phone, String address) {
        // diagram 1 - register account
        try {
            // validate fullName, checking cannot by empty
            if (fullName == null || fullName.isEmpty()) throw new InvalidInputException("fullname field is empty.", "Fullname field is empty. Try input something.");

            // validate email, checking must be filled, must end with @gmail.com
            if (email == null || email.isEmpty()) throw new InvalidInputException("email field is empty.", "Email field is empty. Try input something.");
            if (email.endsWith("@gmail.com")) throw new InvalidInputException("email does not end with '@gmail.com'.", "Inputted email must end with '@gmail.com'.");

            // validate password
            if (password.length() < 6) throw new InvalidInputException("password is less than 6 characters.", "Password must be at least 6 characters long.");

            //validate confirm_password
            if (password.equals(confirm_password) == false) throw new InvalidInputException("password does not match confirm password", "Password and Confirm Password must have same value.");

            // validate phone
            if (phone == null || phone.isEmpty()) throw new InvalidInputException("phone field is empty.", "Phone field is empty. Try input something.");

            if (phone.length() >= 10 && phone.length() <= 13) throw new InvalidInputException("phone does not have 10 to 13 digits long. ", "Phone should be 10 to 13 digits long.");
            if (Checking.isNumeric(phone) == false) throw new InvalidInputException("phone contains other than numeric values.", "Phone must only contain numeric value.");

            // validate address
            if (address == null || address.isEmpty()) throw new InvalidInputException("address field is empty.", "Address field is empty. Try input something.");

            Customer.insert(fullName, email, password, confirm_password, phone, address);
        } catch (NoRowsAffectedException e) {
            e.getUserMessage();
        } catch (InvalidInputException e) {
            e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your register is successful!";
    }

    public String login(String email, String password) {
        // login
        try {
            if (email.equals("") || email == null) throw new InvalidInputException("email field is empty.", "Email field is empty. Try input something.");

            if (password.equals("") || password == null) throw new InvalidInputException("password field is empty.", "Password field is empty. Try input something..");
            HashMap<String,String> credentials = User.getCredentialsByEmail(email, password);;
            if (credentials.get("email").equals(email) == false) throw new NotFoundException("Email is not found. Please try another email or register.");
            if (credentials.get("password").equals(password) == false) throw new NotFoundException("Password is wrong.");
        } catch (InvalidInputException e) {
            e.getUserMessage();
        } catch (NotFoundException e) {
            e.getUserMessage();
        } catch (SQLException e) {
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your login is successful!";
    }
}
