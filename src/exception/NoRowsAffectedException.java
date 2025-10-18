package exception;

public class NoRowsAffectedException extends Exception {
    private final String userMessage;

    public NoRowsAffectedException(String userMessage) {
        super("no rows are affected");
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

}
