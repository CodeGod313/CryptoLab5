package com.cleverdeath.cryptolabfifth.service;

import java.math.BigInteger;
import java.util.List;

public interface DigitalSignatureService {
    List<Integer> signTextFile(String filePath, String signedFIlePath);
    List<BigInteger> generateSignature(String text, int q, int p, int g, int x);
    boolean verifySignature(List<Integer> keys, String filePath);
}