package com.cleverdeath.cryptolabfifth.service.impl;

import com.cleverdeath.cryptolabfifth.service.DigitalSignatureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DigitalSignatureServiceImplTest {

    DigitalSignatureService digitalSignatureService;
    String inputFilePath;
    String outputFilePath;

    @BeforeAll
    void setUp() {
        digitalSignatureService = new DigitalSignatureServiceImpl();
        inputFilePath = "src/test/resources/input.txt";
        outputFilePath = "src/test/resources/output.txt";
    }

    @Test
    void signTextFileAndVerify() {
        List<Integer> keys = digitalSignatureService.signTextFile(inputFilePath, outputFilePath);
        Assertions.assertTrue(digitalSignatureService.verifySignature(keys, outputFilePath));
    }
}