package response;

public class SingleObjectResponse<T> {
    private final String errorUserMessage;
    private final T object;
    
    public SingleObjectResponse(String errorUserMessage, T object) {
        this.errorUserMessage = errorUserMessage;
        this.object = object;
    }

    public boolean isErrorUserMessage() {
        return errorUserMessage != null;
    }

    public boolean isObject() {
        return object != null;
    }

    public String getErrorUserMessage() {
        return errorUserMessage;
    }

    public T getHashMap() {
        return object;
    }
}
