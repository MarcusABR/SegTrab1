package br.com.segcomp.aes;

import java.math.BigInteger;

public interface RandomNumberGenerator {

    BigInteger generateNumber(int length);
}
