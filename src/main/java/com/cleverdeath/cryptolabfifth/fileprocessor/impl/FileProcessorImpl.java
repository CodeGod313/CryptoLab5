package com.cleverdeath.cryptolabfifth.fileprocessor.impl;

import com.cleverdeath.cryptolabfifth.fileprocessor.FileProcessor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileProcessorImpl implements FileProcessor {
    @Override
    public Optional<String> readStringFromFile(Path path) {
        try {
            return Optional.of(new String(Files.readAllBytes(path)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void writeStringToFile(String text, String filePath) {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(filePath, StandardCharsets.UTF_8);
            printWriter.write(text);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
