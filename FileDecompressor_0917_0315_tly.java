// 代码生成时间: 2025-09-17 03:15:51
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class FileDecompressor {

    /**
     * Unzips a file from a specified source path to a target directory.
     *
     * @param sourcePath The path to the zip file.
     * @param targetDirectory The directory where the files will be extracted.
     * @throws IOException If an I/O error occurs.
     */
    public void unzip(String sourcePath, String targetDirectory) throws IOException {
        // Make sure the target directory exists
        Files.createDirectories(Paths.get(targetDirectory));

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(sourcePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            // Iterate through the ZIP entries
            while (entry != null) {
                String filePath = targetDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the ZIP entry is a file, then extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the ZIP entry is a directory, then create it
                    Files.createDirectories(Paths.get(filePath));
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            System.err.println("The file not found: " + sourcePath);
        } catch (IOException e) {
            System.err.println("An IOException occurred: " + e.getMessage());
        }
    }

    /**
     * Extracts a file from a ZIP input stream.
     *
     * @param zipIn The ZIP input stream.
     * @param filePath The path to extract the file to.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    public static void main(String[] args) {
        FileDecompressor decompressor = new FileDecompressor();
        try {
            decompressor.unzip("path/to/source.zip", "path/to/target/directory");
            System.out.println("Decompression completed successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred during decompression: " + e.getMessage());
        }
    }
}