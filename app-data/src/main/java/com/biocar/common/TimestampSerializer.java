package com.biocar.common;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author DeSen Xu
 * @date 2021-11-08 10:14
 */
public class TimestampSerializer extends JsonSerializer<Timestamp> {


    @Override
    public void serialize(Timestamp s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String time = String.valueOf(s.toInstant().toEpochMilli());
        jsonGenerator.writeString(time);
    }
}
