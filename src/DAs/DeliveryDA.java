package DAs;

import java.util.HashMap;

import App.Connect;
import Helpers.Result;
import Queries.DeliveryQueries;

public class DeliveryDA {
    private static DeliveryDA deliveryDA;
    private Connect connection = Connect.getInstance();

    public static DeliveryDA getDeliveryDA() {
        if (deliveryDA == null) deliveryDA = new DeliveryDA();
        return deliveryDA;
    }

    public Result read(String idOrder, String idCourier) {
        String query = DeliveryQueries.generateReadQuery(idOrder, idCourier);
        return connection.execQuery(query);
    }

    public HashMap<String, Object> saveStatusDA(String idOrder, String status) {
        String query = DeliveryQueries.generateUpdateStatusQuery(idOrder, status);
        return connection.execUpdate(query);
    }

    public Result saveDA(String idOrder, String idCourier) {
        String query = DeliveryQueries.generateRegisterQuery(idOrder, idCourier);
        return connection.execQuery(query);
    }
}
