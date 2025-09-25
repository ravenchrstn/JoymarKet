package Validators;

import Models.Courier;
import Models.Customer;
import Models.OrderHeader;
import Models.Product;
import Models.Promo;

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

    public boolean validateIdOrderExist(String idOrder) {
        OrderHeader oh = OrderHeader.getOrderHeader(idOrder);
        if (oh.getIdOrder() == null) return false;
        return true;
    }

    public boolean validateIdCourierExist(String idCourier) {
        Courier oh = Courier.getCourier(idCourier);
        if (oh.getIdUser() == null) return false;
        return true;
    }
}
