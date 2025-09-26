package Controller;

import java.util.ArrayList;
import Models.Courier;

public class CourierHandler {
    public ArrayList<Courier> getAllCouriers() {
        // diagram 10 - view all couriers
        return Courier.getAllCouriers();
    }
}
