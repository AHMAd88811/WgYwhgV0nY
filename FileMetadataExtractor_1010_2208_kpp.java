// 代码生成时间: 2025-10-10 22:08:47
package com.example.filemetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import play.mvc.Controller;
import play.mvc.Result;

public class FileMetadataExtractor extends Controller {

    // 此方法提取并返回文件的元数据信息
    public Result getFileMetadata(String filePath) {
        try {
            File file = new File(filePath);

            // 检查文件是否存在
            if (!file.exists()) {
                return badRequest("File does not exist.");
            }

            // 检查是否为文件
            if (!file.isFile()) {
                return badRequest("Path is not a file.");
            }

            // 获取文件属性
            BasicFileAttributes attrs = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);
            FileTime creationTime = attrs.creationTime();
            FileTime lastModifiedTime = attrs.lastModifiedTime();
            boolean isRegularFile = attrs.isRegularFile();
            long size = attrs.size();

            // 构建元数据信息对象
            FileMetadata metadata = new FileMetadata(
                file.getName(),
                creationTime.toString(),
                lastModifiedTime.toString(),
                isRegularFile,
                size
            );

            // 将元数据信息对象转换为JSON并返回
            return ok(Json.toJson(metadata));
        } catch (IOException e) {
            // 处理IO异常
            return internalServerError("An error occurred while processing the file.");
        }
    }

    // 内部类用于保存文件元数据
    public static class FileMetadata {
        public String name;
        public String creationTime;
        public String lastModifiedTime;
        public boolean isRegularFile;
        public long size;

        public FileMetadata(String name, String creationTime, String lastModifiedTime, boolean isRegularFile, long size) {
            this.name = name;
            this.creationTime = creationTime;
            this.lastModifiedTime = lastModifiedTime;
            this.isRegularFile = isRegularFile;
            this.size = size;
        }
    }
}
