package auth;

import model.User;

public abstract class SessionManager {
    private static final long EXPIRY_DURATION = 60 * 60 * 1000; // 60 minutes
    private static long loggedInAt = 0;
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void saveSession(User u) {
        user = u;
        loggedInAt = System.currentTimeMillis();
    }

    public static void clearSession() {
        user = null;
        loggedInAt = 0;
    }

    public static void validateSession() {
        if ((loggedInAt + EXPIRY_DURATION) <= System.currentTimeMillis()) clearSession();
    }
}
