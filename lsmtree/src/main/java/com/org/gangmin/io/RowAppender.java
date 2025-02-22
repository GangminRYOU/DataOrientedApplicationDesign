package com.org.gangmin.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class RowAppender {
    private static final String KEY_VAL_SEPARATOR = ",";
    private static final DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ObjectMapper objectMapper;
    private final Environment env;


    public <T> void append(Data<T> data){
        try {
            String row = new StringBuilder()
                    .append(data.getId()).append(KEY_VAL_SEPARATOR).append(objectMapper.writeValueAsString(data.getObject())).append(System.lineSeparator()).toString();
            String tablespace = env.getProperty("app.tablespace");
            Files.write(Paths.get(tablespace).resolve(LocalDateTime.now().format(fomatter) + ".txt"), row.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }catch (IOException ex){
            throw new IllegalArgumentException("Data Append Failure detail(" + ex.getMessage() + ")");
        }
    }
}
