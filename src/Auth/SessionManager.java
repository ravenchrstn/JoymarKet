package Auth;

import Models.User;

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

    // public static boolean isSessionValid() throws FileNotFoundException, IOException {
    //     Properties prop = new Properties();
    //     try (FileInputStream fis = new FileInputStream(FILENAME)) {
    //         prop.load(fis);
    //     }

    //     String loggedInAtStr = prop.getProperty("loggedInAt");
    //     if (loggedInAtStr == null) return false;

    //     long loggedInAt = Long.parseLong(loggedInAtStr);
    //     long now = System.currentTimeMillis();

    //     if ((now - loggedInAt) >= EXPIRY_DURATION) return false;
    //     return true;
    // }
}
