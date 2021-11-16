package com.biocar.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author DeSen Xu
 * @date 2021-11-16 22:35
 */
public class BooleanSerializer extends JsonSerializer<Boolean> {
    @Override
    public void serialize(Boolean aBoolean, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(aBoolean ? 1 : 0);
    }
}
