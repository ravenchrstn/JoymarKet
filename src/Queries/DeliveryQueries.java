package Queries;

public abstract class DeliveryQueries {
    public static String generateUpdateStatusQuery(String idOrder, String status) {
        return "UPDATE deliveries SET status = " + status + " WHERE idOrder = " + idOrder;
    }

    public static String generateReadQuery(String idOrder, String idUser) {
        return "SELECT idOrder, idUser, status FROM deliveries WHERE idOrder = " + idOrder + " AND idUser = " + idUser;
    }
}
