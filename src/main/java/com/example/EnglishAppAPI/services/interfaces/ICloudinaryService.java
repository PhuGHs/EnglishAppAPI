package com.example.EnglishAppAPI.services.interfaces;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public interface ICloudinaryService {
    Map<String, Object> uploadFile(String base64Image, String publicId, boolean overwrite, boolean invalidate) throws IOException;
}
