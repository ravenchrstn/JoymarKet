package Queries;

public class PromoQueries {
    
    public static String generateReadQuery(String idPromo) {
        return "SELECT idPromo, code, headline, discountPercentage FROM promoDA WHERE idPromo = " + idPromo;
    }

    public static String generateReadByCodeQuery(String code) {
        return "SELECT idPromo, code, headline, discountPercentage FROM promoDA WHERE code = " + code;
    }
}
