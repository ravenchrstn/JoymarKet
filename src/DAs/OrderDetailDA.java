package DAs;

import java.util.HashMap;

import App.Connect;
import Helper.Result;
import Queries.OrderDetailQueries;

public class OrderDetailDA {
    private static OrderDetailDA orderDetailDA;
    private Connect connect = Connect.getInstance();

    public static OrderDetailDA getOrderDetailDA() {
        if (orderDetailDA == null) orderDetailDA = new OrderDetailDA();
        return orderDetailDA;
    }

    // public HashMap<String, Object> saveDetailDA(String idProduct, int qty) {
    //     HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
    //     connect.execUpdate(OrderDetailQueries.);
    // }

    public Result read(String idOrder, String idProduct) {
        return connect.execQuery(OrderDetailQueries.generateReadQuery(idOrder, idProduct));
    }

    public HashMap<String, Object> saveDetailDA(String idOrder, String idProduct, int qty) {
        return connect.execUpdate(OrderDetailQueries.generateRegisterQuery(idOrder, idProduct, qty));
    }
}
