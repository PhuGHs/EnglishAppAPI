package com.example.EnglishAppAPI.services.impls;

import com.cloudinary.Cloudinary;
import com.example.EnglishAppAPI.services.interfaces.ICloudinaryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService implements ICloudinaryService {
    @Resource
    private Cloudinary cloudinary;
    @Override
    public Map<String, Object> uploadFile(String base64Image, String publicId, boolean overwrite, boolean invalidate) throws IOException {
        Map<String, Object> uploadOptions = new HashMap<>();
        uploadOptions.put("public_id", publicId);
        uploadOptions.put("overwrite", overwrite);
        uploadOptions.put("invalidate", invalidate);

        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = cloudinary.uploader().upload(base64Image, uploadOptions);
        return uploadResult;
    }
}
