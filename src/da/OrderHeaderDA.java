package da;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;
import model.OrderHeader;

public class OrderHeaderDA {
	private Connect connect = Connect.getInstance();
	private static OrderHeaderDA orderHeaderDA;

	public static OrderHeaderDA getOrderHeaderDA() {
		if (orderHeaderDA == null)
			orderHeaderDA = new OrderHeaderDA();
		return orderHeaderDA;
	}

	// create new order in OrderHeader
	public String insert(String idUser, String idPromo, Double totalAmount)
			throws NoRowsAffectedException, SQLException {

		String query = "INSERT INTO orderheader (idCustomer, idPromo, status, orderedAt, totalAmount) "
				+ "VALUES (?, ?, ?, NOW(), ?)";

		PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, idUser);
		ps.setString(2, idPromo);
		ps.setString(3, "pending");
		ps.setDouble(4, totalAmount);

		int rows = ps.executeUpdate();
		if (rows <= 0) {
			throw new NoRowsAffectedException("Failed to add order header.");
		}

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next())
			return rs.getString(1);

		return null;
	}

	// diagram 8 - view order history
	public ArrayList<HashMap<String, Object>> findOrderHistoriesByIdUser(String idUser) throws SQLException {
		String query = "SELECT oh.idOrder, oh.orderedAt, oh.totalAmount, od.qty, p.idProduct, p.name FROM orderheader oh JOIN order_details od ON oh.idOrder = od.idOrder JOIN product p ON od.idProduct = p.idProduct JOIN (SELECT idOrder, MIN(idProduct) FROM orderdetail GROUP BY idOrder) od_single ON od.idOrder = od_single.idOrder AND od_single.idProduct = od.idProduct WHERE oh.idUser = "
				+ idUser + " ORDER BY oh.orderedAt DESC;";
		ResultSet rs = connect.execQuery(query);
		ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("idOrder", rs.getString("idOrder"));
			hm.put("idProduct", rs.getString("idProduct"));
			hm.put("name", rs.getString("name"));
			hm.put("orderedAt", rs.getString("orderedAt"));
			hm.put("totalAmount", rs.getString("totalAmount"));
			hm.put("qty", rs.getString("qty"));
			al.add(hm);
		}
		return al;
	}

	// diagram 11 - view all orders
	public ArrayList<HashMap<String, Object>> findAllOrders() throws SQLException {
		String query = "SELECT oh.idOrder, oh.orderedAt, oh.totalAmount, oh.status, od.qty, p.idProduct, p.name FROM orderheader oh JOIN orderdetail od ON oh.idOrder = od.idOrder JOIN product p ON od.idProduct = p.idProduct JOIN (SELECT idOrder, MIN(idProduct) FROM orderdetail GROUP BY idOrder) od_single ON od.idOrder = od_single.idOrder AND od_single.idProduct = od.idProduct ORDER BY oh.orderedAt DESC;";
		ResultSet rs = connect.execQuery(query);
		ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("idOrder", rs.getString("idOrder"));
			hm.put("idProduct", rs.getString("idProduct"));
			hm.put("name", rs.getString("name"));
			hm.put("status", rs.getString("status"));
			hm.put("orderedAt", rs.getString("orderedAt"));
			hm.put("totalAmount", rs.getString("totalAmount"));
			hm.put("qty", rs.getString("qty"));
			al.add(hm);
		}
		return al;
	}

	// courier fetches all orders that are assigned to them
	public ArrayList<OrderHeader> findAssignedDeliveriesByIdUser(String idUser) throws SQLException {
	    String query =
	        "SELECT oh.idOrder, oh.idCustomer, oh.idPromo, oh.status, " +
	        "oh.orderedAt, oh.totalAmount, d.status AS deliveryStatus " +
	        "FROM orderheader oh " +
	        "JOIN delivery d ON oh.idOrder = d.idOrder " +
	        "WHERE d.idCourier = '" + idUser + "';";

	    ResultSet rs = connect.execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }

	    return list;
	}
	
	// customer view: get ongoing order
	public ArrayList<OrderHeader> findOngoingOrdersByCustomer(String customerId) throws SQLException {
	    String query =
	    	"SELECT oh.*, d.status AS deliveryStatus "
	        + "FROM orderheader oh "
	        + "LEFT JOIN delivery d ON d.idOrder = oh.idOrder "
	        + "WHERE oh.idCustomer = '" + customerId + "' "
	        + "AND (d.status = 'pending' OR d.status = 'in progress' OR d.status IS NULL) "
	        + "AND oh.status != 'cancelled' "
	        + "AND oh.status != 'completed';";

	    ResultSet rs = connect.execQuery(query);

	    ArrayList<OrderHeader> list = new ArrayList<>();
	    while (rs.next()) {
	        OrderHeader oh = OrderHeader.fromResultSet(rs);
	        list.add(oh);
	    }

	    return list;
	}
	
	// customer view: get completed order
	public ArrayList<OrderHeader> findCompletedOrdersByCustomer(String customerId) throws SQLException {
	    String query =
	        "SELECT oh.*, u.fullName AS courierName, d.status AS deliveryStatus "
	        + "FROM orderheader oh "
	        + "JOIN delivery d ON d.idOrder = oh.idOrder "
	        + "LEFT JOIN user u ON u.idUser = d.idCourier "
	        + "WHERE oh.idCustomer = '" + customerId + "' "
	        + "AND d.status = 'completed';";

	    ResultSet rs = connect.execQuery(query);

	    ArrayList<OrderHeader> list = new ArrayList<>();
	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }

	    return list;
	}

	// customer view: get cancelled order:
	public ArrayList<OrderHeader> findCancelledOrdersByCustomer(String customerId) throws SQLException {
	    String query =
	        "SELECT oh.*, d.status AS deliveryStatus "
	        + "FROM orderheader oh "
	        + "JOIN delivery d ON d.idOrder = oh.idOrder "
	        + "WHERE oh.idCustomer = '" + customerId + "' "
	        + "AND d.status = 'cancelled';";

	    ResultSet rs = connect.execQuery(query);

	    ArrayList<OrderHeader> list = new ArrayList<>();
	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }

	    return list;
	}

	// admin: get orders that aren't assigned to a courier yet
	public ArrayList<OrderHeader> findUnassignedOrders() throws SQLException {
	    String query =
	    	"SELECT oh.* " +
	    	"FROM orderheader oh " +
	    	"LEFT JOIN delivery d ON oh.idOrder = d.idOrder " +
	    	"WHERE (d.idCourier IS NULL OR d.idCourier = '') " +
	    	"AND oh.status NOT IN ('cancelled', 'completed')";

	    ResultSet rs = connect.execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }
	    return list;
	}
	
	// admin view: get order/deliveries that have been assigned to a courier.
	public ArrayList<OrderHeader> findAssignedOrders() throws SQLException {
	    String query =
	        "SELECT oh.*, d.status AS deliveryStatus " +
	        "FROM orderheader oh " +
	        "JOIN delivery d ON oh.idOrder = d.idOrder " +
	        "WHERE oh.status NOT IN ('cancelled', 'completed') " +
	        "AND d.status NOT IN ('completed')";

	    ResultSet rs = connect.execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) list.add(OrderHeader.fromResultSet(rs));

	    return list;
	}
	
	// get an assigned courier info
	public String findAssignedCourier(String orderId) throws SQLException {
	    String query =
	        "SELECT u.fullName " +
	        "FROM delivery d " +
	        "JOIN user u ON d.idCourier = u.idUser " +
	        "WHERE d.idOrder = '" + orderId + "'";

	    ResultSet rs = connect.execQuery(query);
	    if (rs.next()) {
	        return rs.getString(1);
	    }
	    return null;
	}
	
	// admin view: get all completed order/deliveries by the courier
	public ArrayList<OrderHeader> findCompletedOrders() throws SQLException {
	    String query =
	        "SELECT oh.* "
	        + "FROM orderheader oh "
	        + "JOIN delivery d ON oh.idOrder = d.idOrder "
	        + "WHERE oh.status = 'completed' "
	        + "AND d.status = 'completed';";

	    ResultSet rs = connect.execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) list.add(OrderHeader.fromResultSet(rs));

	    return list;
	}
	
	// customer pop-up notification when an order is cancelled by the admin.
	public ArrayList<OrderHeader> findUnnotifiedCancelledOrders(String idUser) throws SQLException {
	    String query =
	        "SELECT * FROM orderheader " +
	        "WHERE idCustomer = '" + idUser + "' " +
	        "AND status = 'cancelled' " +
	        "AND customerNotified = 0;";

	    ResultSet rs = connect.execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }

	    return list;
	}
	
	// customer pop-up notification when an order has been completed by the courier.
	public ArrayList<OrderHeader> findUnnotifiedCompletedOrders(String idUser) throws SQLException {
	    String query =
	        "SELECT * FROM orderheader " +
	        "WHERE idCustomer = '" + idUser + "' " +
	        "AND status = 'completed' " +
	        "AND notifiedCompleted = 0";

	    ResultSet rs = Connect.getInstance().execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }

	    return list;
	}
	
	// courier view: list of completed deliveries by them
	public ArrayList<OrderHeader> findCompletedDeliveriesByCourier(String courierId) throws SQLException {
	    String query =
	        "SELECT oh.*, d.status AS deliveryStatus " +
	        "FROM orderheader oh " +
	        "JOIN delivery d ON oh.idOrder = d.idOrder " +
	        "WHERE d.idCourier = '" + courierId + "' " +
	        "AND d.status = 'completed'";

	    ResultSet rs = connect.execQuery(query);
	    ArrayList<OrderHeader> list = new ArrayList<>();

	    while (rs.next()) {
	        list.add(OrderHeader.fromResultSet(rs));
	    }

	    return list;
	}
	
	// marks cancelled order notification as shown, so the notifcation won't pops up again.
	public void updateCustomerNotified(String idOrder) throws SQLException {
	    String query =
	        "UPDATE orderheader SET customerNotified = 1 " +
	        "WHERE idOrder = '" + idOrder + "';";

	    connect.execUpdate(query);
	}
}