package com.cleverdeath.cryptolabfifth.service.impl;

import com.cleverdeath.cryptolabfifth.fileprocessor.FileProcessor;
import com.cleverdeath.cryptolabfifth.fileprocessor.impl.FileProcessorImpl;
import com.cleverdeath.cryptolabfifth.service.DigitalSignatureService;
import com.cleverdeath.cryptolabfifth.service.HashService;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DigitalSignatureServiceImpl implements DigitalSignatureService {

    public static final String NEW_LINE = "\n";
    public static final List<Integer> primeNumbers = new ArrayList<>();
    public static final int R_POSITION = 0;
    public static final int S_POSITION = 1;
    private static boolean isGenerated = false;

    @Override
    public List<Integer> signTextFile(String filePath, String signedFilePath) {
        FileProcessor fileProcessor = new FileProcessorImpl();
        Path path = Paths.get(filePath);
        String text = fileProcessor.readStringFromFile(path).get();
        List<Integer> keys = generateKeys();
        List<BigInteger> signature = generateSignature(text, keys.get(0), keys.get(1), keys.get(2), keys.get(3));
        String textWithSignature = text + NEW_LINE + signature.get(R_POSITION) + NEW_LINE + signature.get(S_POSITION);
        fileProcessor.writeStringToFile(textWithSignature, signedFilePath);
        return keys;
    }

    @Override
    public List<BigInteger> generateSignature(String text, int q, int p, int g, int x) {
        Random random = new Random();
        BigInteger r, s;
        do {
            int k = random.nextInt(q - 1) + 1;
            HashService hashService = new HashServiceImpl();
            r = BigInteger.valueOf(g)
                    .pow(k)
                    .mod(BigInteger.valueOf(p))
                    .mod(BigInteger.valueOf(q));
            BigInteger xMultiplyR = r.multiply(BigInteger.valueOf(x));
            s = hashService.generateHash(text)
                    .add(xMultiplyR)
                    .multiply(BigInteger.valueOf(k).pow(q - 2))
                    .mod(BigInteger.valueOf(q));
        } while (r.equals(BigInteger.valueOf(0)) || s.equals(BigInteger.valueOf(0)));
        List<BigInteger> signature = List.of(r, s);
        return signature;
    }

    @Override
    public boolean verifySignature(List<Integer> keys, String filePath) {
        FileProcessor fileProcessor = new FileProcessorImpl();
        Path path = Paths.get(filePath);
        List<String> lines = fileProcessor.readLines(path);
        BigInteger r = new BigInteger(lines.get(lines.size() - 2));
        BigInteger s = new BigInteger(lines.get(lines.size() - 1));
        StringBuilder textWithoutSignature = new StringBuilder();
        for (int i = 0; i < lines.size() - 2; i++) {
            textWithoutSignature.append(lines.get(i));
        }
        BigInteger w = s
                .pow(keys.get(0) - 2)
                .mod(BigInteger.valueOf(keys.get(0)));
        HashService hashService = new HashServiceImpl();
        BigInteger u1 = hashService.generateHash(textWithoutSignature.toString())
                .multiply(w)
                .mod(BigInteger.valueOf(keys.get(0)));
        BigInteger u2 = r
                .multiply(w)
                .mod(BigInteger.valueOf(keys.get(0)));
        BigInteger v = BigInteger.valueOf(keys.get(2))
                .pow(u1.intValue())
                .multiply(BigInteger.valueOf(keys.get(4)).pow(u2.intValue()))
                .mod(BigInteger.valueOf(keys.get(1)))
                .mod(BigInteger.valueOf(keys.get(0)));
        return v.equals(r);
    }


    private List<Integer> generateKeys() {
        if (!isGenerated) {
            generatePrimeNumbers();
        }
        Random random = new Random();
        int randPrimeIndex = random.nextInt(11 - 4 + 1) + 4;
        int q = primeNumbers.get(randPrimeIndex);
        int indexP = randPrimeIndex + 1;
        while ((primeNumbers.get(indexP) - 1) % q != 0) {
            indexP++;
        }
        int p = primeNumbers.get(indexP);
        int g = BigInteger.valueOf(2).pow((p - 1) / q).mod(BigInteger.valueOf(p)).intValue();
        int x = random.nextInt(q - 1) + 1;
        int y = BigInteger.valueOf(g)
                .pow(x)
                .mod(BigInteger.valueOf(p))
                .intValue();
        List<Integer> keys = List.of(q, p, g, x, y);
        return keys;
    }


    private void generatePrimeNumbers() {
        List<Integer> sieve = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            sieve.add(0);
        }
        for (int i = 2; i * i < 1000; i++) {
            if (sieve.get(i) == 0) {
                for (int j = i * i; j < 1000; j += i) {
                    sieve.set(j, 1);
                }
            }
        }
        for (int i = 2; i < 1000; i++) {
            if (sieve.get(i) == 0) {
                primeNumbers.add(i);
            }
        }
        isGenerated = true;
    }


}

