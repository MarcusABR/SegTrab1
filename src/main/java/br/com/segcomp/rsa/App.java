package br.com.bb.RSA;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Teste {
    public static void main(String[] args) {
        PrimeGenerator pr = new PrimeGenerator(new SecureRandom());

        BigInteger p1 = pr.generate();
        BigInteger p2 = pr.generate();
        BigInteger n = p1.multiply(p2);
        BigInteger phi = p1.subtract(BigInteger.ONE).multiply(p2.subtract(BigInteger.ONE));
        BigInteger e = findE(phi);
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(n);
        System.out.println(phi);
        System.out.println();

        BigInteger d = e.modInverse(p1.multiply(p2));
        System.out.println("Este é o d: "+ d);


        char message = 'A';

        Integer value = Integer.valueOf(message);
        BigInteger cypher = new BigInteger(String.valueOf(value)).modPow(e, n);
        System.out.println("Cifrada: "+ cypher);

        BigInteger decypher = cypher.modPow(d, n);
        System.out.println("Decifrada: "+ decypher);
        //find e
        //com E e N tenho a chvae

        // 
        

        
        // System.out.println(value);
        // System.out.println();     

    }

    public static BigInteger findE(BigInteger phi){
        BigInteger e = new BigInteger("65538");
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }
}
