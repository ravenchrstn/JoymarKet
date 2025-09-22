package Queries;

public abstract class AdminQueries {
    public static String generateReadQuery(String idAdmin) {
        return "SELECT idUser, fullName, email, password, phone, address, role, emergencyContact FROM users WHERE role = 'admin' AND idUser = " + idAdmin;
    }
    // public static String generateRegisterQuery(Admin admin) {
    //     return "INSERT INTO users (fullName, email, password, phone, address, role, emergencyContact) VALUES (" + admin.fullName  + ", " + admin.email + ", " + admin.password + ", " + admin.phone + ", " + admin.address + ", " + admin.emergencyContact + ")";
    // }

    // public static String generateUpdateQuery(Admin admin) {
    //     return "ALTER users SET fullName = " + admin.fullName + ", phone = " + admin.phone + ", address = " + admin.address + ", emergencyContact = " + admin.emergencyContact + " WHERE idUser = " + admin.idUser + " AND role = 'admin'";
    // }

}
