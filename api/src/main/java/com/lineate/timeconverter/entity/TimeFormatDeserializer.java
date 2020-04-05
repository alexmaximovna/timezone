package com.lineate.timeconverter.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * This class deserializes string value in object class TimeFormat
 */
public class TimeFormatDeserializer extends JsonDeserializer<TimeFormat> {
    @Override
    public TimeFormat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node == null) {
            return null;
        }
        String format = node.textValue();

        for (TimeFormat timeFormat : TimeFormat.values()) {
            if (timeFormat.toString().equals(format)) {
                return timeFormat;
            }
        }
        return null;
    }
}
