package com.biocar.serializer;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * 反序列化时间戳
 * 毫秒级时间戳 -> Timestamp
 * @author DeSen Xu
 * @date 2021-11-15 12:57
 */
public class TimestampDeserializer extends JsonDeserializer<Timestamp> {

    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String time = jsonParser.getValueAsString();
        return new Timestamp(Long.parseLong(time));
    }

}
