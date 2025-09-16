// 代码生成时间: 2025-09-16 13:13:42
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A utility class for unzipping files.
 */
public class UnzipUtility {

    /**
     * Unzips the specified zip file to the destination directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The destination directory where the zip file will be extracted.
     * @throws IOException If an I/O error occurs.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        // Check if the zip file exists
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            throw new IOException("Zip file does not exist: " + zipFilePath);
        }

        // Create the destination directory if it doesn't exist
        File destinationDir = new File(destDirectory);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        // Create a new ZipInputStream
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry = zipIn.getNextEntry();
            // Iterate through the entries in the zip file
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, create it
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    /**
     * Extracts a single file from the zip input stream to the specified path.
     *
     * @param zipIn The ZipInputStream to read from.
     * @param filePath The path to extract the file to.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            // Read and write the file in chunks
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        UnzipUtility unzipUtility = new UnzipUtility();
        try {
            unzipUtility.unzip("path/to/your.zip", "path/to/destination");
            System.out.println("Unzipping completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during unzipping.");
        }
    }
}
