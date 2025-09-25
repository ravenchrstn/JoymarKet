package DAs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import App.Connect;
import Helpers.Result;
import Models.Admin;
import Models.Courier;
import Models.Customer;
import Models.User;
import Queries.UserQueries;

public class UserDA {
    private Connect connect = Connect.getInstance();
    public static UserDA userDA;

    public static UserDA getUserDA() {
        if (userDA == null) userDA = new UserDA();
        return userDA;
    }

    public String registerCourier(String fullName, String email, String password, String phone, String address) { 
        // diagram 1 register courier
        String query = "INSERT INTO users (fullName, email, password, phone, address, role, balance) VALUES (" + fullName + ", " + email + ", " + password + ", " + phone + ", " + address + ", Customer, 0)";
        return this.connect.execUpdate(query);
    }

    public User read(String idUser) {
        String query = UserQueries.generateReadQuery(idUser);
        Result res = this.connect.execQuery(query);
        User user = null;
        try {
            while (res.getRs().next()) {
                String role = res.getRs().getString("role");
                if (role == "Customer") {
                    user = Customer.fromResultSet(res.getRs());
                } else if (role == "Courier") {
                    user = Courier.fromResultSet(res.getRs());
                } else if (role == "Admin") {
                    user = Admin.fromResultSet(res.getRs());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<User> read(ArrayList<String> idsUser) {
        String query = UserQueries.generateReadQuery(idsUser);
        Result res = this.connect.execQuery(query);
        ArrayList<User> users = new ArrayList<User>();

        try {
            while (res.getRs().next()) {
                String role = res.getRs().getString("role");
                User user = null;
                if (role == "Customer") {
                    user = Customer.fromResultSet(res.getRs());
                } else if (role == "Courier") {
                    user = Courier.fromResultSet(res.getRs());
                } else if (role == "Admin") {
                    user = Admin.fromResultSet(res.getRs());
                }
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public <R extends User> ArrayList<User> read(ArrayList<String> idsUser, Function<ResultSet, R> factories, Class<R> className) {
        // membaca hanya dengan role tertentu
        String query = "";
        if (className == Customer.class) {
            query = UserQueries.generateReadQuery(idsUser);
        } else if (className == Admin.class) {
            query = UserQueries.generateReadQuery(idsUser);
        } else if (className == Courier.class) {
            query = UserQueries.generateReadQuery(idsUser);
        }
        
        Result res = this.connect.execQuery(query);
        ArrayList<User> users = new ArrayList<User>();

        try {
            while (res.getRs().next()) {
                User user = factories.apply(res.getRs());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public ArrayList<Courier> readCouriers() {
        String query = UserQueries.generateReadCourierQuery();
        Result res = this.connect.execQuery(query);

        ArrayList<Courier> couriers = new ArrayList<Courier>();
        try {
            while (res.getRs().next()) {
                Courier courier = Courier.fromResultSet(res.getRs());
                couriers.add(courier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return couriers;
    }

    public boolean topUpBalance(String idCustomer, Double newBalance) {
        String query = UserQueries.generateUpdateQuery(idCustomer, newBalance);
        HashMap<String, Object> hashMap = this.connect.execUpdate(query);
        int rowsAffected = (int) hashMap.get("rowsAffected");

        if (rowsAffected <= 0) return false;
        return true;
    }

    public int saveDA(String idUser, String fullName, String phone, String address) {
        // edit profile
        HashMap<String, Object> hashMap = this.connect.execUpdate(UserQueries.generateUpdateQuery(idUser, fullName, phone, address));
        int rowsAffected = (int) hashMap.get("rowsAffected");
        return rowsAffected;
    }
}
