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
        int red = node.get("red").asInt();
        int green = node.get("green").asInt();
        int blue = node.get("blue").asInt();
        int alpha = node.has("transparency") ? node.get("transparency").asInt() : 255; // Default to fully opaque if no transparency

        return new Color(red, green, blue, alpha);
    }
}
