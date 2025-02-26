package com.pablodiazjorge.crud.services;

import com.cloudinary.Cloudinary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.Object;
import java.util.Map;

public interface CloudinaryService {
    Map<String, Object> upload(MultipartFile multipartFile) throws IOException;

    Map<String, Object> delete(String id) throws IOException;
}


