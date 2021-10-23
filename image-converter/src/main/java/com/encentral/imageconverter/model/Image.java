package com.encentral.imageconverter.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

@JsonSerialize(using = ImageSerializer.class)
public class Image {
    private String url;
    private String fileName;
    private String mimeType;
    private Date dateCreated;

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getUrl() {
        return url;
    }

    private String invertedUrl = null;
    private String identifier;



    public String getInvertedUrl() {
        return invertedUrl;
    }

    public void setInvertedUrl(String invertedUrl) {
        this.invertedUrl = invertedUrl;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id='" + identifier + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}
