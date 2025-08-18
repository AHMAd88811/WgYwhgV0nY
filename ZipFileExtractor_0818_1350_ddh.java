// 代码生成时间: 2025-08-18 13:50:41
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.libs.Files;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files as JavaFiles;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileExtractor extends Controller {

    /**
     * Handles the extraction of a zip file.
     * 
     * @param tempZipFile The temporary zip file to extract.
     * @return A Result object indicating the success or failure of the operation.
     */
    public Result extractZipFile(Files.TemporaryFile tempZipFile) {
        try {
            // Read the zip file into a byte array
            ByteArrayOutputStream baous = new ByteArrayOutputStream();
            OutputStream os = baous;
            InputStream is = tempZipFile.asInputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) >= 0) {
                os.write(buffer, 0, length);
            }
            os.close();

            // Convert the byte array back to an InputStream for extraction
            ByteArrayInputStream bais = new ByteArrayInputStream(baous.toByteArray());
            ZipInputStream zis = new ZipInputStream(bais);
            ZipEntry zipEntry;

            // Extract each entry from the zip file
            while ((zipEntry = zis.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                // Create the file
                Path filePath = Paths.get(fileName);
                JavaFiles.createDirectories(filePath.getParent());
                OutputStream fileOS = Files.newOutputStream(filePath);

                // Write the file content
                byte[] bytes = new byte[1024];
                int lengthFile;
                while ((lengthFile = zis.read(bytes)) >= 0) {
                    fileOS.write(bytes, 0, lengthFile);
                }
                fileOS.close();
                zis.closeEntry();
            }
            zis.close();

            // Delete the temporary file after extraction
            tempZipFile.deleteOnExit();

            // Return a success message
            return ok("Zip file extracted successfully.");
        } catch (IOException e) {
            // Handle any I/O errors that occur during extraction
            return internalServerError("An error occurred while extracting the zip file: " + e.getMessage());
        }
    }
}
