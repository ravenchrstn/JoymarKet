package Controller;

import java.util.ArrayList;
import java.util.HashMap;

import Models.Courier;

public class CourierHandler {
    public ArrayList<HashMap<String, Object>> getCouriers() {
        // diagram 10, agak beda dari diagram
        return Courier.getCouriers();
    }
}
