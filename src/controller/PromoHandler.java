package controller;

import java.sql.SQLException;
import java.util.HashMap;
import da.PromoDA;

public class PromoHandler {
    private PromoDA promoDA = PromoDA.getPromoDA();

    public Double getDiscountByCode(String code) {
        try {
            HashMap<String,Object> info = promoDA.findPromoInfoByCode(code);
            if (info == null || !info.containsKey("discountPercentage")) return null;
            return ((Number) info.get("discountPercentage")).doubleValue();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
