// 代码生成时间: 2025-09-04 21:43:50
import play.Application;
import play.Logger;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.EssentialAction;
import play.mvc.PathBindable;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import static play.mvc.Controller.request;
import static play.mvc.Results.ok;
import org.apache.commons.io.FileUtils;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import play.mvc.Result;

public class ImageResizer extends Application {

    public static void main(String[] args) {
        GuiceApplicationBuilder builder = new GuiceApplicationBuilder();
        start(builder);
    }

    public static class ImageResizeAction extends EssentialAction {

        private final int width;
        private final int height;

        public ImageResizeAction(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public CompletionStage<Result> execute() {
            String uploadDir = "public/uploads";
            String fileName = request().getQueryString("file");

            if (fileName == null) {
                return CompletableFuture.completedFuture(badRequest("File name is missing"));
            }

            File originalFile = new File(uploadDir, fileName);

            if (!originalFile.exists()) {
                return CompletableFuture.completedFuture(notFound("File not found"));
            }

            try {
                BufferedImage originalImage = ImageIO.read(originalFile);
                if (originalImage == null) {
                    return CompletableFuture.completedFuture(notFound("File is not a valid image"));
                }

                File resizedFile = new File(uploadDir, "resized_" + fileName);
                BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(originalImage.getSubimage(0, 0, width, height), 0, 0, null);
                resizedImage.flush();
                g2d.dispose();

                ImageIO.write(resizedImage, "jpg", resizedFile);
                return CompletableFuture.completedFuture(ok("Image resized successfully"));
            } catch (IOException e) {
                Logger.error("Error resizing image", e);
                return CompletableFuture.completedFuture(internalServerError("Error resizing image"));
            }
        }
    }
}
