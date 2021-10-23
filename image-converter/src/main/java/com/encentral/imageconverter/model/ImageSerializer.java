package com.encentral.imageconverter.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ImageSerializer extends StdSerializer<Image> {

    public ImageSerializer() {
        this(null);
    }

    public ImageSerializer(Class<Image> t) {
        super(t);
    }

    @Override
    public void serialize(
            Image value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeStringField("id", value.getIdentifier());
        jgen.writeStringField("name", value.getFileName());
        if(value.getInvertedUrl() != null) jgen.writeStringField("inverted_url", value.getInvertedUrl());
        jgen.writeEndObject();
    }
}