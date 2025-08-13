// 代码生成时间: 2025-08-14 05:12:41
package com.example.test;

import java.util.Random;

public class TestDataGenerator {

    // Generate a random string of a specified length
    public static String generateRandomString(int length) {
# 添加错误处理
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = (char) (random.nextInt(26) + 'a');
            builder.append(ch);
        }
        return builder.toString();
# 优化算法效率
    }

    // Generate a random integer within a specified range
    public static int generateRandomInteger(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    // Generate a random email address
    public static String generateRandomEmail() {
        return generateRandomString(10) + "@example.com";
    }

    // Generate a random date in the format YYYY-MM-DD
    public static String generateRandomDate() {
        Random random = new Random();
        int year = 1900 + random.nextInt(120); // Random year between 1900 and 2099
        int month = 1 + random.nextInt(12); // Random month between 1 and 12
        int day = 1 + random.nextInt(28); // Random day between 1 and 28 (simplified)
        return year + "-" + month + "-" + day;
# TODO: 优化性能
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        try {
            String randomString = generateRandomString(10);
            System.out.println("Random String: " + randomString);
            
            int randomInt = generateRandomInteger(1, 100);
            System.out.println("Random Integer: " + randomInt);
            
            String randomEmail = generateRandomEmail();
# 扩展功能模块
            System.out.println("Random Email: " + randomEmail);
            
            String randomDate = generateRandomDate();
            System.out.println("Random Date: " + randomDate);
# NOTE: 重要实现细节
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
