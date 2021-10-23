package com.encentral.imageconverter.model;

import com.encentral.scaffold.entities.JpaImage;

public class ImageMapper {
    public static JpaImage imageToJpaImage(Image image) {
        JpaImage jpaImage = new JpaImage();
        jpaImage.setIdentifier(image.getIdentifier());
        jpaImage.setInvertedUrl(image.getInvertedUrl());
        jpaImage.setDateCreated(image.getDateCreated());
        jpaImage.setMimeType(image.getMimeType());
        jpaImage.setUrl(image.getUrl());
        jpaImage.setFileName(image.getFileName());
        return jpaImage;

    }

    public static Image jpaImageToImage(JpaImage image) {

        Image jpaImage = new Image();

        jpaImage.setIdentifier(image.getIdentifier());
        jpaImage.setInvertedUrl(image.getInvertedUrl());
        jpaImage.setDateCreated(image.getDateCreated());
        jpaImage.setMimeType(image.getMimeType());
        jpaImage.setUrl(image.getUrl());
        jpaImage.setFileName(image.getFileName());
        return jpaImage;
    }
}
