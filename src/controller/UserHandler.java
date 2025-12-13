package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import auth.SessionManager;
import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import exception.NotFoundException;
import helper.Checking;
import model.Courier;
import model.Customer;
import model.User;
import response.MultipleObjectsResponse;

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
            if (!email.endsWith("@gmail.com")) throw new InvalidInputException("email does not end with '@gmail.com'.", "Inputted email must end with '@gmail.com'.");

            // validate password
            if (password.length() < 6) throw new InvalidInputException("password is less than 6 characters.", "Password must be at least 6 characters long.");

            //validate confirm_password
            if (password.equals(confirm_password) == false) throw new InvalidInputException("password does not match confirm password", "Password and Confirm Password must have same value.");

            // validate phone
            if (phone == null || phone.isEmpty()) throw new InvalidInputException("phone field is empty.", "Phone field is empty. Try input something.");
            if (phone.length() < 10 || phone.length() > 13) throw new InvalidInputException("phone does not have 10 to 13 digits long. ", "Phone should be 10 to 13 digits long.");
            if (Checking.isNumeric(phone) == false) throw new InvalidInputException("phone contains other than numeric values.", "Phone must only contain numeric value.");

            // validate address
            if (address == null || address.isEmpty()) throw new InvalidInputException("address field is empty.", "Address field is empty. Try input something.");

            // user insert
            int userId = Customer.insert(fullName, email, password, confirm_password, phone, address);
            // customer insert
            Customer.insertBalance(userId, 0.0);
            
        } catch (NoRowsAffectedException e) {
            return e.getUserMessage();
        } catch (InvalidInputException e) {
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your register is successful!";
    }
    
    public String login(String email, String password) {
    	// kalau ada order yang cancelled, dia nampilin alert
        User user = SessionManager.getUser();
        if (user != null) {
        	SessionManager.saveSession(user);
        	return "Your login is successful!";
        }
        
        try {
            if (email == null || email.isEmpty())
                throw new InvalidInputException("email field is empty.", "Email field is empty. Try input something.");

            if (password == null || password.isEmpty())
                throw new InvalidInputException("password field is empty.", "Password field is empty. Try input something..");

            HashMap<String, String> credentials = User.getCredentialsByEmail(email, password);

            if (!credentials.get("email").equals(email))
                throw new NotFoundException("Email is not found. Please try another email or register.");

            if (!credentials.get("password").equals(password))
                throw new NotFoundException("Password is wrong.");

            // ngambil user
            User loggedUser = User.getUserByEmail(email);
            if (loggedUser == null) {
            	return "No user is found!";
            }

            // ngesave session
            SessionManager.saveSession(loggedUser);

        } catch (InvalidInputException e) {
            return e.getUserMessage();
        } catch (NotFoundException e) {
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        
        return "Your login is successful!";
        
    }
    
    public String TopUpBalance(String userId, String input) {
		try {
			
            if (input == null || input.isBlank()) throw new InvalidInputException("Top Up Amount is empty.", "Top Up Amount is empty. Try input something.");
            
            double amount;

            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException ex) {
            	throw new InvalidInputException("Must be numeric.", "Please input a valid number.");
            }
			
            if (amount < 10000) throw new InvalidInputException("Top Up Amount is less than 10000.", "Top Up Amount must be at least 10000.");

            Double balance = Customer.getBalanceByIdUser(userId);
            balance = balance + amount;
            Customer.updateBalanceByIdUser(userId, balance);
            
        } catch (NoRowsAffectedException e) {
            return e.getUserMessage();
        } catch (InvalidInputException e) {
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Your Top Up is successful!";
	}
    
    public String editProfile(String userId, String fullName, String phone, String address) {
		try {
			
            if (fullName == null || fullName.isBlank()) throw new InvalidInputException("Full Name is empty.", "Full Name is empty. Try input something.");
            if (phone == null || phone.isBlank()) throw new InvalidInputException("Phone is empty.", "Phone is empty. Try input something.");
            if (address == null || address.isBlank()) throw new InvalidInputException("Address is empty.", "Address is empty. Try input something.");
            
            if (Checking.isNumeric(phone) == false) throw new InvalidInputException("Phone contains other than numeric values.", "Phone must only contain numeric value.");
            if (phone.length() < 10 || phone.length() > 13) throw new InvalidInputException("Invalid Phone number length.", "Phone number must be 10–13 digits.");

            User.updateUserById(userId, fullName, phone, address);

            User updatedUser = User.getUserDA().getUserByEmail(SessionManager.getUser().getEmail());
            SessionManager.saveSession(updatedUser);

            
        } catch (NoRowsAffectedException e) {
            return e.getUserMessage();
        } catch (InvalidInputException e) {
            return e.getUserMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occured while processing your request. Please try again later.";
        }
        return "Edit Profile is successful!";
	}
    
    public User reloadUser(String idUser) {
        try {
            return User.getUserDA().getUserById(idUser);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
