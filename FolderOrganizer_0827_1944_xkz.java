// 代码生成时间: 2025-08-27 19:44:02
import java.io.File;
# NOTE: 重要实现细节
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
# 改进用户体验
import java.util.stream.Collectors;

/**
 * FolderOrganizer is a utility class to organize files within a directory.
 * It sorts the files alphabetically and moves them into subdirectories based on the first letter of their names.
 */
public class FolderOrganizer {

    // The root directory to organize
    private final Path rootDirectory;

    public FolderOrganizer(String rootDirectoryPath) {
        this.rootDirectory = Paths.get(rootDirectoryPath);
    }

    /**
     * Organizes the files within the root directory.
     * @throws IOException If an I/O error occurs.
     */
    public void organizeFiles() throws IOException {
        if (Files.notExists(rootDirectory)) {
            throw new IOException("Root directory does not exist: " + rootDirectory);
        }

        // Get all files in the root directory
        List<Path> files = Files.list(rootDirectory)
                .filter(Files::isRegularFile)
                .sorted(Comparator.comparing(Path::getFileName))
                .collect(Collectors.toList());

        for (Path file : files) {
            char firstLetter = file.getFileName().toString().charAt(0);
            Path targetDirectory = rootDirectory.resolve(firstLetter + "s");

            // Create the subdirectory if it does not exist
            if (Files.notExists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // Move the file to the subdirectory
            Path targetPath = targetDirectory.resolve(file.getFileName());
            Files.move(file, targetPath);
        }
    }

    /**
     * Main method to run the FolderOrganizer.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FolderOrganizer <root_directory_path>");
            System.exit(1);
        }
# 优化算法效率

        String rootDirectoryPath = args[0];
        FolderOrganizer organizer = new FolderOrganizer(rootDirectoryPath);
        try {
            organizer.organizeFiles();
            System.out.println("Files organized successfully.");
# TODO: 优化性能
        } catch (IOException e) {
            System.err.println("Error organizing files: " + e.getMessage());
        }
    }
}
# 增强安全性
