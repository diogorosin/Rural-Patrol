package br.com.developen.ruralpatrol.jersey;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

    public Boolean deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        return ! "0".equals(parser.getText());

    }

}