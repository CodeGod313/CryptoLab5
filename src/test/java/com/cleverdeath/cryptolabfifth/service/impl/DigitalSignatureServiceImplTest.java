package com.cleverdeath.cryptolabfifth.service.impl;

import com.cleverdeath.cryptolabfifth.service.DigitalSignatureService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
    void signTextFile() {
        digitalSignatureService.
    }

    @Test
    void generateSignature() {
    }

    @Test
    void verifySignature() {
    }
}