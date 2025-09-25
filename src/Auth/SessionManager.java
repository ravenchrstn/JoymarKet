package Auth;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import Models.User;

public abstract class SessionManager {
    private static final String FILENAME = "session.properties";
    private static final long EXPIRY_DURATION = 60 * 60 * 1000; // 60 minutes
    private static User user;

    public static User getUser() throws IOException {
        if (user == null) user = loadUser();
        return user;
    }

    public static void save(String userId, String username, String role) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("userId", userId);
        prop.setProperty("username", username);
        prop.setProperty("role", role);
        prop.setProperty("loggedInAt", String.valueOf(System.currentTimeMillis()));

        try (FileOutputStream fos = new FileOutputStream(FILENAME)) {
            prop.store(fos, "Session Data");   
        }
    }

    public static User loadUser() throws IOException {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(FILENAME)) {
            prop.load(fis);
        }
        return User.getUser(prop.getProperty("userId"));
    }

    public static void validateSession() throws FileNotFoundException, IOException {
        if (isSessionValid() == false) clearSession();
    }

    public static boolean isSessionValid() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(FILENAME)) {
            prop.load(fis);
        }

        String loggedInAtStr = prop.getProperty("loggedInAt");
        if (loggedInAtStr == null) return false;

        long loggedInAt = Long.parseLong(loggedInAtStr);
        long now = System.currentTimeMillis();

        if ((now - loggedInAt) >= EXPIRY_DURATION) return false;
        return true;
    }

    public static void clearSession() throws FileNotFoundException, IOException {
        Properties prop = new Properties();

        try (FileOutputStream fos = new FileOutputStream(FILENAME)) {
            prop.store(fos, "Cleared File");
        }
    }
}
