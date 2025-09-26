package Queries;

public abstract class DeliveryQueries {
    public static String generateUpdateStatusQuery(String idOrder, String status) {
        return "UPDATE deliveries SET status = " + status + " WHERE idOrder = " + idOrder;
    }

    public static String generateReadQuery(String idOrder, String idCourier) {
        return "SELECT idOrder, idCourier, status FROM deliveries WHERE idOrder = " + idOrder + " AND idCourier = " + idCourier;
    }
}
