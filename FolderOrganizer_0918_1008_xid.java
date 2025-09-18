// 代码生成时间: 2025-09-18 10:08:38
package com.example.folderorganizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
# TODO: 优化性能
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FolderOrganizer {

    /**
     * 整理指定文件夹的文件结构。
     *
     * @param folderPath 需要整理的文件夹路径。
     * @param categories 文件分类。
     * @throws IOException 如果发生I/O异常。
     */
    public void organize(String folderPath, List<String> categories) throws IOException {
        // 验证输入
        if (folderPath == null || folderPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Folder path cannot be null or empty.");
        }

        // 验证分类
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories list cannot be null or empty.");
        }

        // 创建文件夹路径
        Path folder = Paths.get(folderPath);
# 改进用户体验
        File folderFile = folder.toFile();

        // 确保目录存在
        if (!folderFile.exists() || !folderFile.isDirectory()) {
# NOTE: 重要实现细节
            throw new IOException("The specified folder does not exist or is not a directory.");
        }
# 增强安全性

        // 遍历文件夹中的文件
        File[] files = folderFile.listFiles();
        if (files != null) {
            for (File file : files) {
# 增强安全性
                // 忽略子文件夹
                if (file.isDirectory()) {
                    continue;
                }
# TODO: 优化性能

                // 根据文件类型进行分类
                String fileName = file.getName();
                String extension = extractExtension(fileName);
# 扩展功能模块
                String category = categories.stream()
                        .filter(c -> fileName.endsWith("." + c))
                        .findFirst()
                        .orElse("Others"); // 如果没有匹配的分类，则归为Others

                // 创建新的目录
                Path categoryPath = folder.resolve(category);
                if (!Files.exists(categoryPath)) {
                    Files.createDirectory(categoryPath);
                }

                // 移动文件到相应的分类目录
                Path targetPath = categoryPath.resolve(fileName);
                Files.move(file.toPath(), targetPath);
            }
        }
    }

    /**
     * 从文件名中提取文件扩展名。
     *
     * @param fileName 文件名。
     * @return 文件扩展名。
     */
    private String extractExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        } else {
# 扩展功能模块
            return ""; // 无扩展名
        }
    }

    public static void main(String[] args) {
        try {
            FolderOrganizer organizer = new FolderOrganizer();
            String folderPath = "./some-folder";
            List<String> categories = Arrays.asList("txt", "jpg", "png", "docx", "pdf");
            organizer.organize(folderPath, categories);
            System.out.println("Folder organization completed.");
        } catch (IOException e) {
            System.err.println("An error occurred while organizing the folder: " + e.getMessage());
# FIXME: 处理边界情况
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }
}
