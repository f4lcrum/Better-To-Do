package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.awt.Color;
import java.io.IOException;

public class ColorJsonDeserializer extends JsonDeserializer<Color> {

    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        int r = node.get("r").asInt();
        int g = node.get("g").asInt();
        int b = node.get("b").asInt();
        int alpha = node.get("alpha").asInt();

        return new Color(r, g, b, alpha);
    }
}
