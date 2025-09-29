package Exceptions;

public class InsufficientBalanceException extends Exception {
    private final String userMessage;

    public InsufficientBalanceException(String userMessage) {
        super("balance is insufficient");
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
    
}
