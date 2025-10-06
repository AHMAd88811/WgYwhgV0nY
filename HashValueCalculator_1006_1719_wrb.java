// 代码生成时间: 2025-10-06 17:19:51
 * A utility class for calculating hash values of strings using different hashing algorithms.
 *
 * @author [Your Name]
 * @version 1.0
 */
package com.example.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import play.mvc.Controller;
import play.mvc.Result;

public class HashValueCalculator extends Controller {
    
    private static final String HASH_ALGORITHM_SHA_256 = "SHA-256";
    private static final String HASH_ALGORITHM_MD_5 = "MD5";
    private static final String HASH_ALGORITHM_SHA_1 = "SHA-1";

    /**
     * Calculate the hash value of a given string using the specified algorithm.
     *
     * @param input The string to hash.
     * @param algorithm The hashing algorithm to use.
     * @return The hash value as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified algorithm is not available.
     */
    private String calculateHash(String input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(input.getBytes());
        byte[] hashBytes = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            hexString.append(String.format("%02x", hashByte));
        }
        return hexString.toString();
    }

    /**
     * Action method to calculate and return the hash value of a given string.
     *
     * @param input The string to hash.
     * @param algorithm The hashing algorithm to use.
     * @return A JSON result with the hash value.
     */
    public Result calculateHashValue(String input, String algorithm) {
        try {
            String hashValue = calculateHash(input, algorithm);
            return ok(hashValue);
        } catch (NoSuchAlgorithmException e) {
            return badRequest("Invalid hashing algorithm: " + algorithm);
        }
    }

    // Additional methods for different algorithms can be added here.
}