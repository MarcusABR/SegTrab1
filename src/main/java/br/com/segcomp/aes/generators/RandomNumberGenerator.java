package br.com.segcomp.aes.generators;

import java.math.BigInteger;

public interface RandomNumberGenerator {

    BigInteger generateNumber(int length);
}
