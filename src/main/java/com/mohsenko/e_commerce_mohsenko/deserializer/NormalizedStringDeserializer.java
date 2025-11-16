package com.mohsenko.e_commerce_mohsenko.deserializer;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

// this deserializer is responsible to remove the leading, trailing, and multiple consecutive spaces
public class NormalizedStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser json, DeserializationContext deserializationContext) throws IOException, JacksonException {
        // fetch the string
        String value = json.getValueAsString();

        // handle NullPointerException
        if (value == null) { return null; }

        // trim it & replace multiple consecutive spaces and whitespaces with only one
        return value.trim().replaceAll("\\s+", " ");
    }
}
