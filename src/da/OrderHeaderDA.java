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

	public ArrayList<HashMap<String, Object>> findOrderHistoriesByIdUser(String idUser) throws SQLException {
		// diagram 8 - view order history
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

	public ArrayList<HashMap<String, Object>> findAllOrders() throws SQLException {
		// diagram 11 - view all orders
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

	public ArrayList<OrderHeader> findAssignedDeliveriesByIdUser(String idUser) throws SQLException {
		// diagram 13 - view assigned deliveries
		String query = "SELECT idOrder, idUser, status, orderedAt, totalAmount FROM orderheader oh JOIN delivery d ON oh.idOrder = d.idOrder JOIN user u ON d.idUser = u.idUser WHERE u.role = 'courier' AND d.idUser = "
				+ idUser + ";";
		ResultSet rs = connect.execQuery(query);
		ArrayList<OrderHeader> ohs = new ArrayList<OrderHeader>();
		while (rs.next()) {
			OrderHeader oh = OrderHeader.fromResultSet(rs);
			ohs.add(oh);
		}

		return ohs;
	}
}
