package com.cleverdeath.cryptolabfifth.service.impl;

import com.cleverdeath.cryptolabfifth.service.HashService;

import java.math.BigInteger;

public class HashServiceImpl implements HashService {
    public static final BigInteger FNV_PRIME =  new BigInteger("35835915874844867368919076489095108449946327955754392558399825615420669938882575126094039892345713852759");
    public static final BigInteger FNV_OFFSET_BASIC = new BigInteger("9659303129496669498009435400716310466090418745672637896108374329434462657994582932197716438449813051892206539805784495328239340083876191928701583869517785");
    public static final BigInteger MODULE = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084096");

    @Override
    public BigInteger generateHash(String text) {
        BigInteger hash = new BigInteger(FNV_OFFSET_BASIC.toString());
        for (int i = 0; i < text.length(); i++) {
            hash = hash
                    .xor(BigInteger.valueOf(text.charAt(i)))
                    .multiply(FNV_PRIME)
                    .mod(MODULE);
        }
        return hash;
    }
}
