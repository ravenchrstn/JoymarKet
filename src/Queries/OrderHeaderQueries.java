package Queries;

import Models.OrderHeader;

public abstract class OrderHeaderQueries {
    public static String generateChangeStatusQuery(String idOrder, String status) {
        return "UPDATE order_headers SET status = " + status + " WHERE idOrder = " + idOrder;
    }

    public static String generateReadQuery(String idOrder) {
        return "SELECT idOrder, idCustomer, idPromo, status, orderedAt, totalAmount FROM order_headers WHERE idOrder = " + idOrder;
    }

    public static String generateReadQuery() {
        return "SELECT idOrder, idCustomer, idPromo, status, orderedAt, totalAmount FROM order_headers";
    }

    public static String generateReadCustomerOrderHeaderQuery(String idOrder, String idCustomer) {
        return "SELECT idOrder, idCustomer, idPromo, status, orderedAt, totalAmount FROM order_headers WHERE idOrder = " + idOrder + " AND idCustomer = " + idCustomer;
    }

    public static String generateRegisterQuery(OrderHeader orderHeader) {
        return "INSERT INTO order_headers(idCustomer, idPromo, status, orderedAt, totalAmount) VALUES (" + orderHeader.getIdCustomer() + ", " + orderHeader.getIdPromo() + ", " + orderHeader.getStatus() + ", " + orderHeader.getOrderedAt() + ", " + orderHeader.getTotalAmount();
    }

    public static String generateUpdateTotalAmountQuery(String idOrder, Double totalAmount) {
        return "UPDATE order_headers SET totalAmount = " + totalAmount + " WHERE idOrder = " + idOrder;
    }

    public static String generateReadTotalAmountQuery(String idOrder) {
        return "SELECT * total_amount FROM order_headers WHERE idOrder = " + idOrder;
    }
}
