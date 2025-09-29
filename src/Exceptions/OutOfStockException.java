package Exceptions;

public class OutOfStockException extends Exception {
    private final Integer remainingStock;
    private final String userMessage;

    public OutOfStockException(String message, String userMessage, Integer remainingStock) {
        super(message);
        this.userMessage = userMessage;
        this.remainingStock = remainingStock;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public Integer getRemainingStock() {
        return remainingStock;
    }

}
