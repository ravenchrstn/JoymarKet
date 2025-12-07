package da;

import java.sql.SQLException;
import java.util.HashMap;

import app.Connect;
import exception.NoRowsAffectedException;

public class OrderDetailDA {
	private static OrderDetailDA orderDetailDA;
	private Connect connect = Connect.getInstance();

	public static OrderDetailDA getOrderDetailDA() {
		if (orderDetailDA == null)
			orderDetailDA = new OrderDetailDA();
		return orderDetailDA;
	}

	public void insert(String idOrder, String idProduct, Integer qty) throws NoRowsAffectedException, SQLException {

		String query = "INSERT INTO orderdetail(idOrder, idProduct, qty) VALUES ('" + idOrder + "', '" + idProduct
				+ "', " + qty + ");";

		HashMap<String, Object> hm = this.connect.execUpdate(query);

		int rowsAffected = (Integer) hm.get("rowsAffected");

		if (rowsAffected <= 0) {
			throw new NoRowsAffectedException("Failed to add order detail.");
		}
	}

}
