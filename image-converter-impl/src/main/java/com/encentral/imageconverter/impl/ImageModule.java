package com.encentral.imageconverter.impl;




import com.encentral.imageconverter.api.IImage;
import com.google.inject.AbstractModule;

public class ImageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IImage.class).to(ImageImpl.class);
    }
}
