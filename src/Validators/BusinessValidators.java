package Validators;

import Models.Courier;
import Models.Customer;
import Models.OrderHeader;
import Models.Product;
import Models.Promo;
import Models.User;

public class BusinessValidators {
    private final UserValidators userValidators;

    public BusinessValidators(UserValidators userValidators) {
        this.userValidators = userValidators;
    }

    public boolean validateTopUpBalance(Double amount) {
        if (amount < 10000) return false;
        return true;
    }

    public boolean validateSufficientBalanceCheckout(String idCustomer, String promoCode, Double orderTotal) {
        Double customerBalance = Customer.getCustomer(idCustomer).getBalance();
        Double discountPercentage = Promo.getPromo(promoCode).getDiscountPercentage();
        if (customerBalance < (orderTotal * (100 - discountPercentage) / 100)) return false;
        return true;
    }

    public boolean validateEditProfile(String fullName, String phone, String address) {
        return this.userValidators.validateFullName(fullName) && this.userValidators.validatePhone(phone) && this.userValidators.validateAddress(address);
    }

    public boolean validateProductCountToCart(String idProduct, Integer count) {
        if (count > Product.getAvailableProduct(idProduct).getStock()) return false;
        return true;
    }

    public boolean doesOrderExist(String idOrder) {
        // diagram 12 - business validators
        String idOrderReturn = OrderHeader.findIdOrder(idOrder);
        if (idOrderReturn == null) return false;
        return true;
    }

    public boolean doesCourierExist(Courier oh) {
        // diagram 12 - business validators
        if (oh.getIdUser() == null) return false;
        return true;
    }

    public boolean isCourier(User user) {
        // diagram 12 - business validators
        if (user.getRole().equals("courier") == false) return false;
        return true;
    }
}
