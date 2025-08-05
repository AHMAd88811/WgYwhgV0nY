// 代码生成时间: 2025-08-06 00:00:12
package com.example.tools;

import play.libs.Codec;
import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * PasswordEncryptionDecryption class provides functionality for encrypting and decrypting passwords.
 */
@Singleton
public class PasswordEncryptionDecryption {

    private static final String ALGORITHM = "AES";
    private static final String CHARSET = "UTF-8";

    /**
     * Encrypts a password using AES encryption.
     *
     * @param password The password to encrypt.
     * @return The encrypted password as a hexadecimal string.
     * @throws Exception If an error occurs during encryption.
     */
    public String encryptPassword(String password) throws Exception {
        // Generate a secret key using AES algorithm
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256, SecureRandom.getInstanceStrong());
        SecretKey secretKey = keyGenerator.generateKey();

        // Convert the secret key to a hexadecimal string
        byte[] keyBytes = secretKey.getEncoded();
        String hexKey = Codec.toHex(keyBytes);

        // Encrypt the password using the secret key
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, ALGORITHM));
        byte[] encryptedBytes = cipher.doFinal(password.getBytes(CHARSET));
        String encryptedPassword = Codec.toHex(encryptedBytes);

        return encryptedPassword;
    }

    /**
     * Decrypts a password that was encrypted using the encryptPassword method.
     *
     * @param encryptedPassword The encrypted password to decrypt as a hexadecimal string.
     * @return The decrypted password.
     * @throws Exception If an error occurs during decryption.
     */
    public String decryptPassword(String encryptedPassword) throws Exception {
        // Convert the encrypted password from hexadecimal to bytes
        byte[] encryptedBytes = Codec.fromHex(encryptedPassword);

        // Get the secret key from the encrypted bytes (assuming the first 32 bytes are the key)
        byte[] keyBytes = new byte[32];
        System.arraycopy(encryptedBytes, 0, keyBytes, 0, keyBytes.length);
        SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);

        // Decrypt the password using the secret key
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes, keyBytes.length, encryptedBytes.length - keyBytes.length);
        String decryptedPassword = new String(decryptedBytes, CHARSET);

        return decryptedPassword;
    }
}
