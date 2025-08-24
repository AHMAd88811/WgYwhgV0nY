// 代码生成时间: 2025-08-25 04:39:53
import play.libs.Files;
import play.mvc.Controller;
import play.mvc.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A controller to handle file decompression functionality.
 */
public class FileDecompressor extends Controller {

    /**
     * Decompress a zip file to a specified directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectoryPath The path to the destination directory.
     * @return A Result object indicating the status of the decompression.
     */
    public static Result decompressFile(String zipFilePath, String destDirectoryPath) {
        try {
            // Check if zip file and destination directory exist
            File zipFile = new File(zipFilePath);
            File destDirectory = new File(destDirectoryPath);
            if (!zipFile.exists()) {
                return badRequest("Zip file does not exist.");
            }
            if (!destDirectory.exists() && !destDirectory.mkdirs()) {
                return internalServerError("Failed to create destination directory.");
            }

            // Open the zip file and decompress it
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destDirectoryPath + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, make the directory
                    new File(filePath).mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();

            return ok("File decompressed successfully.");
        } catch (IOException e) {
            return internalServerError("An error occurred during decompression: " + e.getMessage());
        }
    }

    /**
     * Extract a file from a zip input stream.
     *
     * @param zipIn The ZipInputStream containing the zip file.
     * @param filePath The path to extract the file to.
     * @throws IOException If an I/O error occurs.
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        OutputStream out = new FileOutputStream(filePath);
        byte[] buf = new byte[1024];
        int len;
        while ((len = zipIn.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
    }
}
