// 代码生成时间: 2025-09-18 23:53:43
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
# 优化算法效率
import java.awt.image.ImageObserver;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

public class ImageResizer extends Controller {
    /*
     * Method to resize images in a directory
     * @param sourceDirPath Path to the directory containing source images
     * @param targetDirPath Path to the directory where resized images will be saved
     * @param width Desired width of the resized images
     * @param height Desired height of the resized images
     * @return A Result object indicating success or failure of the operation
     */
    public static Result resizeImages(String sourceDirPath, String targetDirPath, int width, int height) {
        File sourceDir = new File(sourceDirPath);
        File[] files = sourceDir.listFiles();
# FIXME: 处理边界情况
        List<String> resizedFiles = new ArrayList<>();
        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImageFile(file)) {
# 添加错误处理
                        String fileName = file.getName();
                        BufferedImage originalImage = ImageIO.read(file);
                        BufferedImage resizedImage = resizeImage(originalImage, width, height);
                        String targetFilePath = targetDirPath + File.separator + fileName;
                        File targetFile = new File(targetFilePath);
                        ImageIO.write(resizedImage, getFileExtension(fileName), targetFile);
                        resizedFiles.add(targetFilePath);
                    }
                }
            }
# 改进用户体验
        } catch (IOException e) {
            e.printStackTrace();
# TODO: 优化性能
            return internalServerError("Error occurred while resizing images: " + e.getMessage());
        }
        return ok("Resized images saved successfully: " + String.join(",", resizedFiles));
    }

    /*
     * Helper method to check if a file is an image
     * @param file The file to check
     * @return true if the file is an image, false otherwise
# 扩展功能模块
     */
    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /*
     * Helper method to get the file extension
     * @param fileName The name of the file
     * @return The file extension without the dot
     */
# TODO: 优化性能
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /*
     * Method to resize an image
     * @param originalImage The original image to resize
# 添加错误处理
     * @param width Desired width of the resized image
# NOTE: 重要实现细节
     * @param height Desired height of the resized image
     * @return The resized image
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
# 改进用户体验
        g2d.dispose();
        return resizedImage;
    }
# 优化算法效率
}