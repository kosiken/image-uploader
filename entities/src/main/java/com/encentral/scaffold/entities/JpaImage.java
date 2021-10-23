package com.encentral.scaffold.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "images")
public class JpaImage {
    @Column(name="url", nullable=false)
    private String url;

    @Column(name="inverted_url", nullable=true)
    private String invertedUrl;


    @Column(name="file_name", nullable=false)
    private String fileName;
    @Column(name="mime_type", nullable=false)
    private String mimeType;
    @Id
    @Basic(optional = false)
    @Column(name = "image_id")
    private String identifier;


    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
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

}
