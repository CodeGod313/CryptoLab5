package com.cleverdeath.cryptolabfifth.service;

import java.math.BigInteger;

public interface HashService {
    BigInteger generateHash(String text);
}
