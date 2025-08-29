// 代码生成时间: 2025-08-29 13:17:20
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
 * A utility class to unzip archives in Java using Java I/O.
 */
public class UnzipUtility {

    /**
     * Unzips a zip file to a specified directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The destination directory where the zip file will be extracted.
     * @throws IOException If any I/O error occurs during unzipping.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a file from a zip input stream to the specified path.
     *
     * @param zipIn The zip input stream.
     * @param filePath The path where the file will be extracted.
     * @throws IOException If any I/O error occurs during extraction.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * Main method for testing the UnzipUtility.
     *
     * @param args Command line arguments (zip file path and destination directory).
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java UnzipUtility <zip file path> <destination directory>");
            return;
        }
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(args[0], args[1]);
            System.out.println("Unzipping completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred during unzipping.");
        }
    }
}
