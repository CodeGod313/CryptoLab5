package com.cleverdeath.cryptolabfifth.fileprocessor;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface FileProcessor {
    Optional<String> readStringFromFile(Path path);
    void writeStringToFile(String text, String filePath);
    List<String> readLines(Path path);
}
