package exception;

public class NotFoundException extends Exception {
    private final String userMessage;
    public NotFoundException(String userMessage) {
        super("not found");
        this.userMessage = userMessage;
    }
    public String getUserMessage() {
        return userMessage;
    }

}
