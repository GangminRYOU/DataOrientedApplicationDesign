package com.org.gangmin.io;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileLoader {
    private static final String FILE_EXTENSION_SEPARATOR = ".";


    private final Environment env;

    public Path getLatestFile(){
        try {
            List<Path> files = Files.list(Paths.get(env.getProperty("app.tablespace")))
                    .filter(p -> p.getFileName().toString().startsWith(env.getProperty("app.file.prefix")))
                    .sorted().collect(Collectors.toList());
            if(files.isEmpty()){
                return createNewFile();
            } else {
                Path target = files.get(files.size() - 1);
                if(Files.size(target) > Integer.parseInt(env.getProperty("app.file.maxsize"))){
                    return createNewFile();
                }
                return target;
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("File Loading Failure detail(" + ex.getMessage() + ")");
        }
    }

    public Path createNewFile() {
        try{
            String newFileName = env.getProperty("app.file.prefix") + System.currentTimeMillis() + FILE_EXTENSION_SEPARATOR +  env.getProperty("app.file.extension");
            Path newFile = Paths.get(newFileName);
            Files.createFile(newFile);
            return newFile;
        }catch (IOException ex){
            throw new IllegalArgumentException("File Loading Failure detail(" + ex.getMessage() + ")");
        }
    }
}
