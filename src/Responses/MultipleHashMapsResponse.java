package Responses;

import java.util.ArrayList;
import java.util.HashMap;

public class MultipleHashMapsResponse {
    private final String errorUserMessage;
    private final ArrayList<HashMap<String, Object>> hashMap;
    
    public MultipleHashMapsResponse(String errorUserMessage, ArrayList<HashMap<String, Object>> hashMap) {
        this.errorUserMessage = errorUserMessage;
        this.hashMap = hashMap;
    }

    public boolean isErrorUserMessage() {
        return errorUserMessage != null;
    }

    public boolean isHashMap() {
        return hashMap != null;
    }

    public String getErrorUserMessage() {
        return errorUserMessage;
    }

    public ArrayList<HashMap<String, Object>> getHashMap() {
        return hashMap;
    }
    
}
