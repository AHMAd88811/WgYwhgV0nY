// 代码生成时间: 2025-08-07 08:58:37
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import play.libs.F;

/**
 * A utility class for bulk renaming of files.
 */
public class BulkFileRenamer {

    /**
     * Renames a batch of files in a directory based on a naming pattern.
     * 
     * @param directoryPath The path to the directory containing files to rename.
     * @param prefix        The prefix for the new file names.
     * @param startNumber   The starting number for the new file names.
     * @return A list of renamed files with their new names.
     */
    public static List<String> renameFiles(String directoryPath, String prefix, int startNumber) {
        List<String> renamedFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The provided directory path does not exist or is not a directory.");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalStateException("Failed to list files in the directory.");
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                String originalFileName = file.getName();
                String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String newFileName = prefix + startNumber + i + extension;
                File newFile = new File(directory, newFileName);

                try {
                    if (file.renameTo(newFile)) {
                        renamedFiles.add(newFile.getName());
                    } else {
                        throw new IllegalStateException("Failed to rename file: " + originalFileName);
                    }
                } catch (SecurityException e) {
                    throw new SecurityException("Permission denied to rename file: " + originalFileName, e);
                }
            }
        }

        return renamedFiles;
    }

    /**
     * Main method for executing the bulk file renamer tool.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java BulkFileRenamer <directoryPath> <prefix> <startNumber>");
            return;
        }

        String directoryPath = args[0];
        String prefix = args[1];
        int startNumber = Integer.parseInt(args[2]);

        try {
            List<String> renamedFiles = renameFiles(directoryPath, prefix, startNumber);
            System.out.println("Renamed files:");
            for (String fileName : renamedFiles) {
                System.out.println(fileName);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}