package Queries;

import java.util.ArrayList;

public abstract class OrderDetailQueries {
    public static String generateRegisterQuery(String idOrder, String idProduct, int qty) {
        return "INSERT INTO order_details (idOrder, idProduct, qty) VALUES (" + idOrder + ", " + idProduct + ", " + qty + ")";
    }

    public static String generateReadIdsProductQuery(ArrayList<String> idsOrder) {
        String query = "SELECT idProduct FROM order_details WHERE idOrder in (";
        for (int i = 0; i < idsOrder.size(); i++) {
            query += idsOrder.get(i);

            if (i == (idsOrder.size() - 1)) {
                query += ")";
            } else {
                query += ", ";
            }
        }
        
        return query;
    }

    public static String generateUpdateQuery(String idProduct, int qty) {
        return "UPDATE order_details SET idProduct = " + idProduct + ", qty = " + qty;
    }
}
