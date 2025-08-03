// 代码生成时间: 2025-08-03 19:38:59
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.libs.Files;
import play.libs.streams.Accumulator;
import play.twirl.api.Content;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static play.mvc.Results.ok;
import static play.mvc.Results.badRequest;

/**
 * ImageResizer class provides functionality to batch resize images.
 */
public class ImageResizer extends Controller {

    /**
     * Handles the batch image resizing request.
     * @return A Result with the batch processing result.
     */
    public Result resizeImages() {
        try {
            Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
            if (formData == null || formData.getFiles().isEmpty()) {
                return badRequest("No files uploaded.");
            }

            List<File> files = formData.getFiles().stream()
                .map(Http.MultipartFormData.FilePart::getFile)
                .collect(Collectors.toList());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (File file : files) {
                BufferedImage originalImage = ImageIO.read(file);
                // Assuming resize to a fixed dimension for simplicity
                int targetWidth = 800; // You can parameterize this
                int targetHeight = 600; // You can parameterize this
                BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);
                ImageIO.write(resizedImage, "JPEG", out);
            }

            return ok(out.toByteArray())
                    .as("image/jpeg");
        } catch (IOException e) {
            return internalServerError("An error occurred while resizing images: " + e.getMessage());
        }
    }

    /**
     * Resizes an image to the specified width and height.
     * @param originalImage The original image to resize.
     * @param targetWidth The desired width.
     * @param targetHeight The desired height.
     * @return The resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Implement your resizing logic here.
        // This is a simple example and may not maintain aspect ratio.
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        return resizedImage;
    }
}
