package com.biocar.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将Date类转换为字符串, 例如: 2021-11-01
 * @author DeSen Xu
 * @date 2021-11-16 21:10
 */
public class DateSerializer extends JsonSerializer<Date> {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
        jsonGenerator.writeString(format.format(date));
    }
}
