package exception;

public class InvalidInputException extends Exception {
    private final String userMessage;

    public InvalidInputException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

}
