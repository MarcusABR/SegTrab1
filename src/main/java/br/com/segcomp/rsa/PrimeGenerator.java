package br.com.segcomp.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;

public class PrimeGenerator {
    private Random randomGenerator;
    private final int BITS = 1024;
    private final BigInteger BGN_INTRVL = BigInteger.ONE.shiftLeft(BITS-1);
    private final BigInteger END_INTRVL = BigInteger.ONE.shiftLeft(BITS).subtract(BigInteger.ONE);

    public PrimeGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
        // SecureRandom
    }

    public BigInteger generate(){
        int idx = 0;
        BigInteger value = getRandom();
        while(!millerRabin(value, 40)){
            idx++;
            value = getRandom();
        }
        return value;
    }

    public boolean millerRabin(BigInteger n, int rounds){
        BigInteger d = n.subtract(BigInteger.ONE);
        final BigInteger TWO = new BigInteger("2");

        if(n.mod(TWO).equals(BigInteger.ZERO)) {
            return false;
        }
        int k = 0;

        while(d.mod(TWO).equals(BigInteger.ZERO)){
            k++;
            d = d.divide(TWO);
        }

        for(int i = 0; i< rounds;i++){
            BigInteger a = RandomIntegerBelow(n.subtract(BigInteger.ONE));
            BigInteger b = a.modPow(d, n);
            if(b.compareTo(BigInteger.ONE) == 0 || b.compareTo(n.subtract(BigInteger.ONE)) == 0){
                continue;
            }
            int g = 1;
            for(;g<k;g++){
                b = b.modPow(TWO, n);
                if(b.compareTo(BigInteger.ONE) == 0 ){
                    return false;
                }
                if(b.compareTo(n.subtract(BigInteger.ONE)) == 0){
                    break;
                }
            }
            if(g==k){
                return false;
            }
        }
        return true;
    }

    private BigInteger getRandom(){
        BigInteger random = new BigInteger(BITS, randomGenerator);
        return random.add(BGN_INTRVL);
    }

    public  BigInteger RandomIntegerBelow(BigInteger N) {
        byte[] bytes = N.toByteArray();
        BigInteger R;
    
        do {
            randomGenerator.nextBytes(bytes);
            bytes[bytes.length - 1] &= (byte)0x7F; //force sign bit to positive
            R = new BigInteger(bytes);
        } while(R.compareTo(N)>=1);
    
        return R;
    }
    
}

