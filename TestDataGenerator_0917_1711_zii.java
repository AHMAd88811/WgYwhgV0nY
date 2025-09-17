// 代码生成时间: 2025-09-17 17:11:56
import java.util.Random;

/**
 * Test data generator class for PlayFramework
 * This class generates sample data for testing purposes
 */
public class TestDataGenerator {

    private static final Random random = new Random();

    /**
     * Generate a random string
     * @param length The length of the string to generate
     * @return A random string of the specified length
     */
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = (char) (random.nextInt(26) + 'a');
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * Generate a random integer
     * @return A random integer
     */
    private static int generateRandomInt() {
        return random.nextInt();
    }

    /**
     * Generate a random email address
     * @return A random email address
     */
    public static String generateRandomEmail() {
        return generateRandomString(10) + "@example.com";
    }

    /**
     * Generate a random username
     * @return A random username
     */
    public static String generateRandomUsername() {
        return "user_" + generateRandomInt();
    }

    /**
     * Generate a random password
     * @return A random password
     */
    public static String generateRandomPassword() {
        return generateRandomString(8);
    }

    /**
     * Generate test data for a user
     * @return A user object with random data
     */
    public static User generateRandomUser() {
        User user = new User();
        user.setEmail(generateRandomEmail());
        user.setUsername(generateRandomUsername());
        user.setPassword(generateRandomPassword());
        return user;
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            User user = generateRandomUser();
            System.out.println("Generated User: " + user);
        } catch (Exception e) {
            System.err.println("Error generating test data: " + e.getMessage());
        }
    }
}

class User {
    private String email;
    private String username;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{email='" + email + "', username='" + username + "', password='" + password + "'}";
    }
}