package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.Color;
import java.io.IOException;

public class ColorJsonSerializer extends StdSerializer<Color> {

    public ColorJsonSerializer() {
        super(Color.class);
    }

    @Override
    public void serialize(Color value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("r", value.getRed());
        gen.writeNumberField("g", value.getGreen());
        gen.writeNumberField("b", value.getBlue());
        gen.writeNumberField("alpha", value.getAlpha());
        gen.writeEndObject();
    }
}
