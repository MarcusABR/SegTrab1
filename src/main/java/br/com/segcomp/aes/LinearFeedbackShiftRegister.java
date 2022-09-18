package br.com.segcomp.aes;

import javax.crypto.KeyGenerator;
import java.math.BigInteger;
import java.security.SecureRandom;

public class LinearFeedbackShiftRegister implements RandomNumberGenerator{

    @Override
    public BigInteger generateNumber(int length) {
        return fib19(length);
    }

    private BigInteger fib19(int length) {
        StringBuilder key = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        int state = secureRandom.nextInt() & 0x0007FFFF;
        for(int i = 0; i < length; i++){
            int nextBit = state & 0x00000001;
            key.append(nextBit);
            int tap19 = (state & 0x00040000) >> 18;
            int tap18 = (state & 0x00020000) >> 17;
            int tap17 = (state & 0x00010000) >> 16;
            int tap14 = (state & 0x00002000) >> 13;
            int xor = tap14 ^ tap17 ^ tap18 ^ tap19;
            state = state >> 1;
            System.out.println(Integer.toString(state, 2));
            System.out.println(xor);
            if (xor == 1) {
                state = state | 0x00040000;
            }
        }
        System.out.println(key.toString());
        System.out.println(key.toString().length());
        return null;
    }

}
