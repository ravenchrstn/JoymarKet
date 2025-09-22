package DAs;

import App.Connect;
import Helper.Result;
import Queries.PromoQueries;

public class PromoDA {
    private Connect connection = Connect.getInstance();
    private static PromoDA promoDA;

    public static PromoDA getPromoDA() {
        if (promoDA == null) promoDA = new PromoDA();
        return promoDA;
    }

    public Result read(String idPromo) {
        String query = PromoQueries.generateReadQuery(idPromo);
        return this.connection.execQuery(query);
    }

    public Result readByCode(String code) {
        String query = PromoQueries.generateReadByCodeQuery(code);
        return this.connection.execQuery(query);
    }
}
