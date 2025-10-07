// 代码生成时间: 2025-10-07 20:07:52
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * DuplicateFileDetector is a utility class for detecting duplicate files within a directory.
 * It uses hashing to compare file contents and identifies duplicates.
 */
public class DuplicateFileDetector {

    // File hashing algorithm
    private static final String HASH_ALGORITHM = "MD5";

    /**
     * Scans the given directory and detects duplicate files.
     * 
     * @param directoryPath The path to the directory to scan.
     * @return A map of file hashes to lists of duplicate files.
     * @throws IOException If an I/O error occurs during the scanning process.
     */
    public Map<String, List<File>> detectDuplicates(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        Map<String, List<File>> duplicates = new HashMap<>();

        // Iterate over all files in the directory
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                String fileHash = getFileHash(file);
                duplicates.computeIfAbsent(fileHash, k -> new ArrayList<>()).add(file);
            }
        }

        // Remove entries with only one file (no duplicates)
        duplicates.entrySet().removeIf(entry -> entry.getValue().size() < 2);

        return duplicates;
    }

    /**
     * Generates a hash for the given file using the specified hashing algorithm.
     * 
     * @param file The file to hash.
     * @return A string representing the hash of the file.
     * @throws IOException If an I/O error occurs during hashing.
     */
    private String getFileHash(File file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] byteArray = new byte[1024];
                int bytesCount;
                while ((bytesCount = fis.read(byteArray)) != -1) {
                    digest.update(byteArray, 0, bytesCount);
                }
            }
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Hash algorithm not found: " + HASH_ALGORITHM, e);
        }
    }

    /**
     * Main method for testing the DuplicateFileDetector.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            DuplicateFileDetector detector = new DuplicateFileDetector();
            Map<String, List<File>> duplicates = detector.detectDuplicates("/path/to/directory");

            duplicates.forEach((hash, files) -> {
                System.out.println("Hash: " + hash);
                files.forEach(file -> System.out.println("  - " + file.getAbsolutePath()));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}