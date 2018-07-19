package br.com.developen.ruralpatrol.jersey;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NumericBooleanSerializer extends JsonSerializer<Boolean> {

    public void serialize(Boolean bool, JsonGenerator generator, SerializerProvider provider) throws IOException {

        generator.writeString(bool ? "1" : "0");

    }

}