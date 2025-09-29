package Controller;

import java.sql.SQLException;
import java.util.ArrayList;

import Exceptions.InvalidInputException;
import Exceptions.NoRowsAffectedException;
import Exceptions.NotFoundException;
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
            Customer.registerCustomer(fullName, email, password, confirm_password, phone, address);
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
            User.login(email, password);
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
