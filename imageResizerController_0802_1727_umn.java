// 代码生成时间: 2025-08-02 17:27:59
package controllers;
# FIXME: 处理边界情况

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.io.File;
import java.io.IOException;
# FIXME: 处理边界情况
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.io.InputStream;
# NOTE: 重要实现细节
import java.nio.file.Files;
import java.nio.file.Path;
# 改进用户体验
import java.nio.file.Paths;
# 增强安全性
import java.util.List;
import java.util.stream.Collectors;
# NOTE: 重要实现细节
import java.util.stream.Stream;

public class ImageResizerController extends Controller {
# 增强安全性
    // Resizes images in the specified directory with the given dimensions
    public Result resizeImages(String targetDirectory, int width, int height) {
        try {
            // Check if directory exists
# NOTE: 重要实现细节
            File directory = new File(targetDirectory);
            if (!directory.exists() || !directory.isDirectory()) {
                return badRequest("Directory does not exist or is not a directory.");
            }

            // Process each image file in the directory
            List<File> imageFiles = listImageFiles(directory);
            for (File imageFile : imageFiles) {
                resizeImage(imageFile, width, height);
            }

            return ok("Images have been resized successfully.");
        } catch (IOException e) {
            return internalServerError("An error occurred while resizing images: " + e.getMessage());
        }
# 增强安全性
    }

    // Lists all image files in the given directory
    private List<File> listImageFiles(File directory) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(directory.getAbsolutePath()))) {
            return stream
# 添加错误处理
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(file -> isImageFile(file))
                .collect(Collectors.toList());
        }
    }

    // Checks if the file is an image by its extension
    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }
# 改进用户体验

    // Resizes the image to the specified dimensions
# TODO: 优化性能
    private void resizeImage(File imageFile, int width, int height) throws IOException {
# TODO: 优化性能
        // Read the image file
        BufferedImage originalImage = ImageIO.read(imageFile);

        // Calculate the scaling factor and the new size
        Dimension size = new Dimension(width, height);
        BufferedImage resizedImage = new BufferedImage(size.width, size.height, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();

        // Scale the image and paint it on the new image
        g2d.drawImage(originalImage.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();

        // Save the resized image back to the file
        ImageIO.write(resizedImage, getExtension(imageFile), imageFile);
    }
# 增强安全性

    // Gets the file extension to determine the image format
    private String getExtension(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "jpg";
        } else if (fileName.endsWith(".png")) {
            return "png";
        } else if (fileName.endsWith(".gif")) {
            return "gif";
        }
        return "";
    }
}