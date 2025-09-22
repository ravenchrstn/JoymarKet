package Queries;

public abstract class OrderDetailQueries {
    public static String generateRegisterQuery(String idOrder, String idProduct, int qty) {
        return "INSERT INTO order_details (idOrder, idProduct, qty) VALUES (" + idOrder + ", " + idProduct + ", " + qty + ")";
    }

    public static String generateReadQuery(String idOrder, String idProduct) {
        return "INSERT INTO order_details (idOrder, idProduct) VALUES (" + idOrder + ", " + idProduct + ", " + ")";
    }

    public static String generateUpdateQuery(String idProduct, int qty) {
        return "UPDATE order_details SET idProduct = " + idProduct + ", qty = " + qty;
    }
}
