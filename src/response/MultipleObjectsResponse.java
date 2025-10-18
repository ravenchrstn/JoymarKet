package response;

import java.util.ArrayList;
public class MultipleObjectsResponse<T> {
    private final String errorUserMessage;
    private final ArrayList<T> objects;
    
    public MultipleObjectsResponse(String errorUserMessage, ArrayList<T> objects) {
        this.errorUserMessage = errorUserMessage;
        this.objects = objects;
    }

    public boolean isErrorUserMessage() {
        return errorUserMessage != null;
    }

    public boolean isObjects() {
        return objects != null;
    }

    public String getErrorUserMessage() {
        return errorUserMessage;
    }

    public ArrayList<T> getHashMap() {
        return objects;
    }
}
