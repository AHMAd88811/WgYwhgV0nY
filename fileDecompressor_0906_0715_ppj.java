// 代码生成时间: 2025-09-06 07:15:17
 * This class provides a simple interface to decompress common archive formats.
 */
package com.example.utils;

import play.libs.Files;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files as JavaFiles;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileDecompressor {

    private static final String TEMP_DIR = "/tmp/";

    public static void decompressZipFile(String filePath, String destinationDir) throws IOException {
        Path zipFilePath = new File(filePath).toPath();
        Path destinationPath = new File(destinationDir).toPath();
        try (ZipFile zipFile = new ZipFile(zipFilePath.toFile())) {
            for (ZipEntry zipEntry : zipFile.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).toList()) {
                if (!zipEntry.isDirectory()) {
                    Path outPath = destinationPath.resolve(zipEntry.getName());
                    try (java.io.OutputStream out = JavaFiles.newOutputStream(outPath);
                         java.io.InputStream in = zipFile.getInputStream(zipEntry)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = in.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                    }
                }
            }
        }
    }

    public static void decompressGzipFile(String filePath, String destinationDir) throws IOException {
        Path source = new File(filePath).toPath();
        Path target = new File(destinationDir).toPath();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(JavaFiles.newInputStream(source));
             OutputStream output = new FileOutputStream(target.toFile())) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
        }
    }

    public static void handleFileUpload(Http.MultipartFormData multipartFormData) {
        Http.MultipartFormData.FilePart filePart = multipartFormData.getFile("file");
        String fileName = filePart.getFilename();
        String contentType = filePart.getContentType();
        if (filePart != null && (contentType.equals("application/zip") || contentType.equals("application/gzip"))) {
            try {
                Path tempFile = Files.tmpFile(TEMP_DIR, filePart.getFilename());
                filePart.ref().toPath().copyTo(tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                if (contentType.equals("application/zip")) {
                    decompressZipFile(tempFile.toString(), TEMP_DIR);
                } else if (contentType.equals("application/gzip")) {
                    decompressGzipFile(tempFile.toString(), TEMP_DIR);
                }
                // TODO: Handle the decompressed files as needed
            } catch (IOException e) {
                // TODO: Handle error appropriately
                e.printStackTrace();
            }
        } else {
            // TODO: Handle invalid file type
        }
    }

    // TODO: Add more methods to support other archive formats as needed
}
