package com.example.IS216_Dlegent.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.IS216_Dlegent.config.CloudinaryConfig;

@Service
public class CloudinaryService {    

    private final Cloudinary cloudinary;

    public CloudinaryService(CloudinaryConfig config) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", config.getCloudName(),
            "api_key", config.getApiKey(),
            "api_secret", config.getApiSecret()
        ));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File uploadedFile = convertMultiPartToFile(file);
        Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
        uploadedFile.delete(); // Xoá file tạm
        return uploadResult.get("secure_url").toString();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}