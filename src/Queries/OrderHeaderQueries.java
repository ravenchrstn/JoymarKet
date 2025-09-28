package Queries;

import Models.OrderHeader;

public abstract class OrderHeaderQueries {
    public static String generateChangeStatusQuery(String idOrder, String status) {
        return "UPDATE order_headers SET status = " + status + " WHERE idOrder = " + idOrder;
    }

    public static String generateReadCustomerOrderHeaderQuery(String idOrder, String idUser) {
        return "SELECT idOrder, idUser, idPromo, status, orderedAt, totalAmount FROM order_headers WHERE idOrder = " + idOrder + " AND idUser = " + idUser;
    }

    public static String generateRegisterQuery(OrderHeader orderHeader) {
        return "INSERT INTO order_headers(idUser, idPromo, status, orderedAt, totalAmount) VALUES (" + orderHeader.getIdUser() + ", " + orderHeader.getIdPromo() + ", " + orderHeader.getStatus() + ", " + orderHeader.getOrderedAt() + ", " + orderHeader.getTotalAmount();
    }

    public static String generateUpdateTotalAmountQuery(String idOrder, Double totalAmount) {
        return "UPDATE order_headers SET totalAmount = " + totalAmount + " WHERE idOrder = " + idOrder;
    }

    public static String generateReadTotalAmountQuery(String idOrder) {
        return "SELECT * total_amount FROM order_headers WHERE idOrder = " + idOrder;
    }
}
