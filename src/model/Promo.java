package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import da.PromoDA;

public class Promo {
    private String idPromo, code, headline; // or description in sequence diagram 7
    private Double discountPercentage;
    private static final PromoDA promoDA = PromoDA.getPromoDA();

    public Promo(String idPromo, String code, String headline, Double discountPercentage) {
        this.idPromo = idPromo;
        this.code = code;
        this.headline = headline;
        this.discountPercentage = discountPercentage;
    }

    public static Promo fromResultSet(ResultSet rs) throws SQLException {
        return new Promo(rs.getString("idPromo"), rs.getString("code"), rs.getString("headline"), rs.getDouble("discountPercentage"));
    }

    public static HashMap<String, Object> getPromoInfoByCode(String code) throws SQLException {
        // diagram 7 - checkout and place order
        return promoDA.findPromoInfoByCode(code);
    }

    public String getIdPromo() {
        return idPromo;
    }

    public String getCode() {
        return code;
    }

    public String getHeadline() {
        return headline;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }
}