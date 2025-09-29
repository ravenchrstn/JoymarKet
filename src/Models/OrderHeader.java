package Models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAs.OrderHeaderDA;
import Exceptions.InsufficientBalanceException;
import Exceptions.NoRowsAffectedException;
import Exceptions.OutOfStockException;

public class OrderHeader {
    private String idOrder, idUser, idPromo, status;
    private Date orderedAt;
    private Double totalAmount;
    private static final OrderHeaderDA orderHeaderDA = OrderHeaderDA.getOrderHeaderDA();

    public OrderHeader(String idOrder, String idUser, String idPromo, String status, Date orderedAt, Double totalAmount) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idPromo = idPromo;
        this.status = status;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
    }

    public static OrderHeader fromResultSet(ResultSet rs) throws SQLException {
        return new OrderHeader(rs.getString("idOrder"), rs.getString("idUser"), rs.getString("idPromo"), rs.getString("status"), rs.getDate("orderedAt"), rs.getDouble("totalAmount"));
    }

    public static ArrayList<OrderHeader> getAssignedDeliveries(String idUser) throws SQLException {
        // diagram 13 - view assigned deliveries
        return orderHeaderDA.findAssignedDeliveriesByIdUser(idUser);
    }

    public static ArrayList<HashMap<String, Object>> getCustomerOrderHistories(String idUser) throws SQLException {
        // diagram 8 - view order history
        return orderHeaderDA.findOrderHistoriesByIdUser(idUser);
    }

    public static ArrayList<HashMap<String, Object>> getAllOrders() throws SQLException {
        // diagram 11 - view all orders
        return orderHeaderDA.findAllOrders();
    }

    public static void checkoutOrder(String idUser, String code) throws OutOfStockException, InsufficientBalanceException, NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        ArrayList<HashMap<String, Object>> checkoutItems = CartItem.getAllCheckoutItems(idUser);
        Double totalAmount = CartItem.getTotalAmountByUserId(idUser);
        Customer customer = Customer.getCustomerByIdUser(idUser);
        Double balance = customer.getBalance();
        HashMap<String, Object> promoHM = Promo.getPromoInfoByCode(code);

        totalAmount *= (100 - (Double) promoHM.get("discountPercentage"));
        if (balance < totalAmount) throw new InsufficientBalanceException("Your balance is not sufficient for the transaction. Please top up and checkout again.");
        User.updateBalanceByIdUser(idUser, (balance - totalAmount));

        String idOrder = createOrderHeader(idUser, (String) promoHM.get("idPromo"), totalAmount);

        for (int i = 0; i < checkoutItems.size(); i++) {
            HashMap<String, Object> item = checkoutItems.get(i);
            OrderDetail.createOrderDetail(idOrder, (String) item.get("idProduct"), (Integer) item.get("count"));
        }
        CartItem.deleteAllByIdUser(idUser);
    }

    public static String createOrderHeader(String idUser, String idPromo, Double totalAmount) throws NoRowsAffectedException, SQLException {
        // diagram 7 - checkout and place order
        return orderHeaderDA.insert(idUser, idPromo, totalAmount);
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPromo() {
        return idPromo;
    }

    public String getStatus() {
        return status;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
