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
     * @throws IOException If an I/O error occurs during file conversion or deletion.
     */
    @Override
    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return result;
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
     * @throws IOException If an I/O error occurs during the conversion process.
     */
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}