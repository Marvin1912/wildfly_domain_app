package com.marvin.domain.backup.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvin.common.jackson.JacksonMapperQualifier;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Singleton
public class CostExportFileWriter {

    private static final Logger LOGGER = Logger.getLogger(CostExportFileWriter.class.getName());

    @Inject
    @JacksonMapperQualifier
    protected ObjectMapper objectMapper;

    public <T> void writeFile(Path target, Stream<T> dataStream) {

        LOGGER.log(Level.INFO, "Going to write file " + target + "!");

        try (BufferedWriter writer = Files.newBufferedWriter(target)) {
            dataStream.forEach(item -> {
                try {
                    String json = objectMapper.writeValueAsString(item);
                    writer.write(json);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException("Could not write object " + item + "!", e);
                }
            });
            LOGGER.log(Level.INFO, "File " + target + " has been written!");
        } catch (IOException e) {
            throw new RuntimeException("Could not open file " + target + "!", e);
        }
    }
}
