package DAs;

import App.Connect;

public class OrderDetailDA {
    private static OrderDetailDA orderDetailDA;
    private Connect connect = Connect.getInstance();

    public static OrderDetailDA getOrderDetailDA() {
        if (orderDetailDA == null) orderDetailDA = new OrderDetailDA();
        return orderDetailDA;
    }
}
