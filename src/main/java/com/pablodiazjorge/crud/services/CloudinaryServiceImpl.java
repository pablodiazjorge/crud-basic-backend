package com.pablodiazjorge.crud.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{
    private final Cloudinary cloudinary;

    /**
     * Constructor for injecting the Cloudinary dependency.
     *
     * @param cloudinary The Cloudinary instance to be injected, configured with environment-specific
     *                   API key, cloud name, and API secret for secure access to the Cloudinary service.
     */
    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Uploads a file to Cloudinary and returns the result as a map.
     *
     * @param multipartFile The file to upload.
     * @return Map - The upload result containing details such as URL, public ID, etc.
     * @throws IOException If an I/O error occurs during file conversion, upload, or cleanup.
     */
    @Override
    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        try {
            Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            if (!Files.deleteIfExists(file.toPath())) {
                System.err.println("Failed to delete temporary file: " + file.getAbsolutePath());
            }
            return result;
        } catch (Exception e) {
            throw new IOException("Cloudinary upload failed: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an image from Cloudinary using its public ID.
     *
     * @param id The public ID of the image to delete.
     * @return Map - The result of the deletion operation.
     * @throws IOException If an I/O error occurs during the deletion process.
     */
    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    /**
     * Converts a MultipartFile to a File object.
     *
     * @param multipartFile The MultipartFile to convert.
     * @return File - The converted File object.
     * @throws IOException If the MultipartFile is null or empty, or if an I/O error occurs during conversion.
     */
    private File convert(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IOException("MultipartFile is null or empty");
        }
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fo = new FileOutputStream(file)) {
            fo.write(multipartFile.getBytes());
        }
        return file;
    }
}
