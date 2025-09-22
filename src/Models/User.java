package Models;

import java.util.ArrayList;

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

    public static User getUser(String idUser) { // HERE
        // factories is to make objects that are used by children.
        return userDA.read(idUser);
    }

    public static ArrayList<User> getUser(ArrayList<String> idsUser) { // HERE
        // factories is to make objects that are used by children.
        return userDA.read(idsUser);
    }

    // public static User saveDataUser(String fullName, String email, String password, String phone, String address) { 
        // we don't make this because it is strange that there is no way to modify or add emergencyContact from Admin, vehicleType and VehiclePlate from Courier.
        // singkatnya, untuk apa buat insert fullName, email, password, phone, address saja tanpa menyimpan informasi yang juga dimiliki childrennya.
        // kalau kita register menggunakan informasi dari parameter saja, malahan nantinya admin tidak mempunyai emergencyContact, dst
    // }
}