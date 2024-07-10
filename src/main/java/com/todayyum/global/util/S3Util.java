package com.todayyum.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return null;
        }

        String fileName = file.getOriginalFilename();

        String ext = getFileExt(fileName);

        String contentType = switch (ext) {
            case "jpeg", "JPEG" -> "image/jpeg";
            case "png", "PNG" -> "image/png";
            case "jpg", "JPG" -> "image/jpg";
            default -> throw new CustomException(ResponseCode.INVALID_FILE);
        };

        fileName = UUID.randomUUID() + "." + ext;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new CustomException(ResponseCode.S3_UPLOAD_FAILED);
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public String getFileExt(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }

        int lastDotIdx = filename.lastIndexOf(".");
        if (lastDotIdx != -1) {
            return filename.substring(lastDotIdx + 1);
        } else {
            return "";
        }
    }

    public void removeFile(String link) {
        if(link == null) return;

        String filename = link.split(".com/")[1];

        System.out.println("filename: " + filename);

        amazonS3.deleteObject(bucket, filename);
    }

}
