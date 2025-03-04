package com.pablodiazjorge.crud.dto;

import lombok.Data;

@Data
public class BookWithImageDTO {
    private Long id;
    private String title;
    private String author;
    private Integer pages;
    private Double price;
    private Long imageId;
    private String imageName;
    private String imageUrl;
    private String imageImageId;

    public BookWithImageDTO(Long id, String title, String author, Integer pages, Double price,
                            Long imageId, String imageName, String imageUrl, String imageImageId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.imageImageId = imageImageId;
    }
}