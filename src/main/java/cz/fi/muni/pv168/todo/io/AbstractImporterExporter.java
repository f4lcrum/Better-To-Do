package cz.fi.muni.pv168.todo.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.fi.muni.pv168.todo.io.entity.ColorJsonDeserializer;
import cz.fi.muni.pv168.todo.io.entity.ColorJsonSerializer;

import java.awt.Color;

public abstract class AbstractImporterExporter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected AbstractImporterExporter() {
        // Register module to (de)serialize Java.awt.Color
        final SimpleModule colorJsonModule = new SimpleModule();
        colorJsonModule.addSerializer(Color.class, new ColorJsonSerializer());
        colorJsonModule.addDeserializer(Color.class, new ColorJsonDeserializer());
        getObjectMapper().registerModule(colorJsonModule);
        // Register module to be able to interact with Java8 Date&Time API
        objectMapper.registerModule(new JavaTimeModule());
        // Make exported JSON pretty printed
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
