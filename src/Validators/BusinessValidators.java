package Validators;

import Models.Customer;
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

    public boolean validateSufficientBalanceCheckout(String idUser, String promoCode, Double orderTotal) {
        Double customerBalance = Customer.getCustomer(idUser).getBalance();
        Double discountPercentage = Promo.getPromo(promoCode).getDiscountPercentage();
        if (customerBalance < (orderTotal * (100 - discountPercentage) / 100)) return false;
        return true;
    }

    public boolean validateEditProfile(String fullName, String phone, String address) {
        return this.userValidators.validateFullName(fullName) && this.userValidators.validatePhone(phone) && this.userValidators.validateAddress(address);
    }
}
