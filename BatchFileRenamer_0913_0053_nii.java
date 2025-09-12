// 代码生成时间: 2025-09-13 00:53:49
package com.example.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class to rename files in batch using Java and Play Framework.
 * This class is designed to be easy to understand, maintain, and extend.
 */
public class BatchFileRenamer {

    /**
     * Renames files in the specified directory based on a given pattern.
     *
     * @param directoryPath The path to the directory containing files to rename.
     * @param filePattern The pattern to rename files to, e.g., "newName-%d.ext".
     * @param fileExtension The extension of the files to rename.
     * @throws IOException If an I/O error occurs.
     */
    public void renameFiles(String directoryPath, String filePattern, String fileExtension) throws IOException {
        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Directory does not exist or is not a directory: " + directoryPath);
        }

        // Get all files in the directory with the specified extension
        File[] files = directory.listFiles((dir, name) -> name.endsWith("." + fileExtension));
        if (files == null) {
            throw new IOException("Could not list files in directory: " + directoryPath);
        }

        // Rename files based on the file pattern
        Arrays.stream(files).forEach(file -> {
            try {
                // Generate new file name
                String newFileName = String.format(filePattern, file.getName().replaceAll("." + fileExtension, ""));
                Path newFilePath = Paths.get(directoryPath, newFileName + "." + fileExtension);

                // Rename file if the new file name is different
                if (!file.getName().equals(newFileName + "." + fileExtension)) {
                    Files.move(file.toPath(), newFilePath);
                }
            } catch (IOException e) {
                System.err.println("Error renaming file: " + file.getName() + " - " + e.getMessage());
            }
        });
    }

    /**
     * Main method to test the file renaming functionality.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            BatchFileRenamer renamer = new BatchFileRenamer();
            // Example usage: rename all .txt files in a directory to 'newName-%d.txt'
            renamer.renameFiles("/path/to/directory", "newName-%d.%s", "txt");
        } catch (IOException e) {
            System.err.println("Error renaming files: " + e.getMessage());
        }
    }
}
