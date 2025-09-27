package DAs;

import java.util.ArrayList;

import App.Connect;
import Helpers.Result;
import Queries.OrderDetailQueries;

public class OrderDetailDA {
    private static OrderDetailDA orderDetailDA;
    private Connect connect = Connect.getInstance();

    public static OrderDetailDA getOrderDetailDA() {
        if (orderDetailDA == null) orderDetailDA = new OrderDetailDA();
        return orderDetailDA;
    }

    public Result read(ArrayList<String> idsOrder) {
        // diagram 11, tidak ada di diagram
        // read one order detail by idOrder
        return connect.execQuery(OrderDetailQueries.generateReadIdsProductQuery(idsOrder));
    }
}
