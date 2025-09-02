// 代码生成时间: 2025-09-02 21:53:10
 * and is designed for maintainability and extensibility.
 */
package com.example.services;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionService {

    private static final String ALGORITHM = "AES";
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a secret key for encryption and decryption.
     *
     * @return A new SecretKey instance.
     */
    private static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256, random); // 256-bit AES key
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating key", e);
        }
    }

    /**
     * Encrypts a password using the provided secret key.
     *
     * @param password The password to encrypt.
     * @param key The secret key used for encryption.
     * @return The encrypted password as a Base64 encoded string.
     */
    public static String encrypt(String password, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    /**
     * Decrypts a password using the provided secret key.
     *
     * @param encryptedPassword The Base64 encoded encrypted password.
     * @param key The secret key used for decryption.
     * @return The decrypted password.
     */
    public static String decrypt(String encryptedPassword, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }

    public static void main(String[] args) {
        SecretKey key = generateKey();
        String originalPassword = "mysecretpassword";
        String encryptedPassword = encrypt(originalPassword, key);
        String decryptedPassword = decrypt(encryptedPassword, key);

        System.out.println("Original: " + originalPassword);
        System.out.println("Encrypted: " + encryptedPassword);
        System.out.println("Decrypted: " + decryptedPassword);
    }
}
