package da;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import app.Connect;

public class PromoDA {
    private Connect connect = Connect.getInstance();
    private static PromoDA promoDA;

    public static PromoDA getPromoDA() {
        if (promoDA == null) promoDA = new PromoDA();
        return promoDA;
    }

    public HashMap<String, Object> findPromoInfoByCode(String code) throws SQLException {
        String q = "SELECT idPromo, discountPercentage FROM promo WHERE code = '" + code + "';";
        ResultSet rs = this.connect.execQuery(q);

        if (rs != null && rs.next()) {
            HashMap<String, Object> promoInfo = new HashMap<>();
            promoInfo.put("idPromo", rs.getString("idPromo"));
            promoInfo.put("discountPercentage", rs.getDouble("discountPercentage"));
            return promoInfo;
        }

        return null; 
    }

}
