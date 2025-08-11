// 代码生成时间: 2025-08-11 23:02:16
 * It follows best practices and ensures code maintainability and extensibility.
 */
package utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordUtil {

    /*
     * Encrypts a password using AES encryption
     *
     * @param password The password to be encrypted
     * @return The encrypted password
     */
    public static String encrypt(String password) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /*
     * Decrypts a password using AES decryption
     *
     * @param encryptedPassword The encrypted password to be decrypted
     * @return The decrypted password
     */
    public static String decrypt(String encryptedPassword) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(encryptedPassword);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, 0, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    /*
     * Main method for testing purposes
     */
    public static void main(String[] args) {
        try {
            String originalPassword = "password123";
            String encryptedPassword = PasswordUtil.encrypt(originalPassword);
            System.out.println("Encrypted Password: " + encryptedPassword);

            String decryptedPassword = PasswordUtil.decrypt(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);

            if (originalPassword.equals(decryptedPassword)) {
                System.out.println("Encryption and decryption successful!");
            } else {
                System.out.println("Encryption and decryption failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
