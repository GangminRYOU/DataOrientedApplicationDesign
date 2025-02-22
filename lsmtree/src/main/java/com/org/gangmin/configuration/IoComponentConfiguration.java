package com.org.gangmin.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.gangmin.io.RowAppender;
import com.org.gangmin.io.RowReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class IoComponentConfiguration {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RowAppender rowAppender(ObjectMapper objectMapper, Environment env){
        return new RowAppender(objectMapper, env);
    }

    @Bean
    public RowReader rowReader(){
        return new RowReader();
    }
}
