package com.pablodiazjorge.crud.services;

import com.pablodiazjorge.crud.entities.Image;
import com.pablodiazjorge.crud.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(CloudinaryService cloudinaryService, ImageRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imageRepository = imageRepository;
    }

    /**
     * Uploads a file and saves the image details.
     *
     * @param file The multipart file to upload.
     * @return Image - The saved image object with uploaded details.
     * @throws IOException If an I/O error occurs during file upload.
     */
    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");
        Image image = new Image(file.getOriginalFilename(), imageUrl, imageId);
        return imageRepository.save(image);
    }

    /**
     * Deletes an image and its associated record.
     *
     * @param image The image object to delete.
     * @throws IOException If an I/O error occurs during deletion.
     */
    @Override
    public void deleteImage(Image image) throws IOException {
        cloudinaryService.delete(image.getImageId());
        imageRepository.deleteById(image.getId());
    }
}
