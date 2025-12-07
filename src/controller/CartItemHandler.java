package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import exception.InvalidInputException;
import exception.NoRowsAffectedException;
import model.CartItem;
import model.Product;

public class CartItemHandler {

	public String addToCart(String idUser, String idProduct, int amount) throws InvalidInputException {
		Product p = Product.getProductDA(idProduct);
		if (p == null)
			throw new InvalidInputException("Product Not Found.", "The product does not exist.");

		int existingQty = 0;
		try {
			ArrayList<HashMap<String, Object>> cartItems = CartItem.getCartItemsDataByIdCustomer(idUser);
			for (HashMap<String, Object> cartItem : cartItems) {
				if (idProduct.equals(cartItem.get("idProduct"))) {
					Object cnt = cartItem.get("count");
					if (cnt instanceof Number)
						existingQty = ((Number) cnt).intValue();
					break;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return "Failed to read cart.";
		}

		if (amount <= 0)
			throw new InvalidInputException("Count cannot be 0.", "Count must be more than 0.");
		if (existingQty + amount > p.getStock())
			throw new InvalidInputException("Low Stock.", "Stock not enough.");

		try {
			CartItem.add(idUser, idProduct, amount);
		} catch (NoRowsAffectedException e) {
			return e.getUserMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while adding to cart.";
		}
		return "Successfully added to cart!";
	}

	public String delete(String idUser, String idProduct) {
		// diagram 5 - remove cart item
		try {
			CartItem.delete(idUser, idProduct);
		} catch (NoRowsAffectedException e) {
			e.printStackTrace();
			return e.getUserMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occured while processing your request. Please try again later.";
		}
		return "Cart item is successfully deleted.";
	}

	public String updateCount(String idUser, String idProduct, int count) throws InvalidInputException {
		// Validasi awal
		if (count <= 0) {
			throw new InvalidInputException("Invalid Count.", "Count must be more than 0.");
		}

		// Ambil product dari database
		Product p = Product.getProductDA(idProduct);
		if (p == null) {
			throw new InvalidInputException("Product Not Found.", "The product does not exist.");
		}

		// Cek stock
		if (count > p.getStock()) {
			throw new InvalidInputException("Low Stock.", "Stock not enough.");
		}

		try {
			CartItem.updateCount(idUser, idProduct, count);
		} catch (NoRowsAffectedException e) {
			return e.getUserMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while updating the cart.";
		}

		return "Cart count updated successfully!";
	}

	public Double getTotalAmountByCustomerId(String idUser) {
		return CartItem.getTotalAmountByUserId(idUser);
	}

//    public 
	public String checkout(String idUser) {
		try {
			ArrayList<HashMap<String, Object>> items = CartItem.getCartItemsDataByIdCustomer(idUser);
			if (items == null || items.isEmpty()) {
				return "Cart is empty.";
			}
			da.ProductDA productDA = da.ProductDA.getProductDA(); // if you have a get method
			for (HashMap<String, Object> row : items) {
				int count = (row.get("count") instanceof Number) ? ((Number) row.get("count")).intValue() : 0;
				int stock = (row.get("stock") instanceof Number) ? ((Number) row.get("stock")).intValue() : 0;
				if (count > stock) {
					return "Not enough stock: " + row.get("name");
				}
			}
			// reduce stock
			for (HashMap<String, Object> row : items) {
				String idProduct = (String) row.get("idProduct");
				int count = (row.get("count") instanceof Number) ? ((Number) row.get("count")).intValue() : 0;
				productDA.reduceStock(idProduct, count);
			}
			CartItem.deleteAllByIdUser(idUser);
			return "Checkout successful";
		} catch (NoRowsAffectedException e) {
			return e.getUserMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred during checkout.";
		}
	}

}
