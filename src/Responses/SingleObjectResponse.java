package Responses;

public class SingleObjectResponse<T> {
    private final String errorUserMessage;
    private final T object;
    
    public SingleObjectResponse(String errorUserMessage, T object) {
        this.errorUserMessage = errorUserMessage;
        this.object = object;
    }
}
