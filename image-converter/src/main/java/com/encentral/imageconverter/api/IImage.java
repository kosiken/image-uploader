/**
 * You have to have a posgresql server running with the config
 * posgres://localhost:5432/imageconverter
 * username: lion
 * password: password
 * or you can use ur own posgresql config
 */



package com.encentral.imageconverter.api;

import com.encentral.imageconverter.model.Image;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface IImage {
    Optional<Image> uploadImage(File file, String fileName) throws IOException;
    Optional<Image> getImage(String url) throws Exception;
    Optional<Image> getImageByName(String fileName) throws Exception;
}
