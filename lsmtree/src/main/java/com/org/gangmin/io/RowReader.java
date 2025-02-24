package com.org.gangmin.io;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;


@RequiredArgsConstructor
public class RowReader {
    private final FileLoader fileLoader;


    public <T> Data<T> read(int id){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileLoader.getLatestFile().toFile(), "r");
            long fileLength = randomAccessFile.length();
            StringBuilder line = new StringBuilder();

            for (long pointer = fileLength - 1; pointer >= 0; pointer--) {
                randomAccessFile.seek(pointer);
                byte b = randomAccessFile.readByte();

                if (b == '\n') {
                    String lineContent = line.reverse().toString();
                    if (lineContent.startsWith(targetId + ",")) {
                        return lineContent;
                    }
                    line.setLength(0); // Reset StringBuilder
                } else {
                    line.append((char) b);
                }
            }

            // Check the last remaining line
            if (line.length() > 0) {
                String lastLine = line.reverse().toString();
                if (lastLine.startsWith(targetId + ",")) {
                    return lastLine;
                }
            }

            randomAccessFile.close();
            return null; // ID not found
        }catch (IOException ex){

        }

    }
}
