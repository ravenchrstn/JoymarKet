package Models;

import java.util.HashMap;

import DAs.UserDA;

public abstract class User {
    protected String idUser, fullName, email, password, phone, address, role;
    protected static final UserDA userDA = UserDA.getUserDA(); // will be used from anywhere to run getUser() method

    public User(String idUser, String fullName, String email, String password, String phone, String address, String role) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public static String login(String email, String password) {
        if (email.equals("") || email == null) return "Email field is empty. Try input something.";

        if (password.equals("") || password == null) return "Password field is empty. Try input something.";

        HashMap<String, String> hm = userDA.findCredentialsByEmail(email);
        if (hm.get("email").equals(email) == false) return "Email is not found. Please try another email or register.";
        if (hm.get("password").equals(password) == false) return "Password is wrong.";
        return null;
    }
}