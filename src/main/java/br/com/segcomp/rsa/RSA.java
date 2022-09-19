package br.com.segcomp.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSA {
    PrimeGenerator prmGen;
    OAEP oaep;
    Random random;
    List<BigInteger> publicKey;
    List<BigInteger> privateKey;
    byte[] cypher;


    public RSA(PrimeGenerator prmGen, OAEP oaep, Random random) {
        this.prmGen = prmGen;
        this.oaep = oaep;
        this.random = random;
        publicKey = new ArrayList<>();
        privateKey = new ArrayList<>();
    }

    public RSA createKeys(){
        PrimeGenerator pr = new PrimeGenerator(new SecureRandom());

        BigInteger p1 = pr.generate();
        BigInteger p2 = pr.generate();
        BigInteger n = p1.multiply(p2);
        BigInteger phi = p1.subtract(BigInteger.ONE).multiply(p2.subtract(BigInteger.ONE));
        BigInteger e = findE(phi);
        BigInteger d = e.modInverse(phi);
        publicKey = new ArrayList<>();
        privateKey = new ArrayList<>();
        publicKey.add(e);
        publicKey.add(n);
        privateKey.add(d);
        privateKey.add(n);

        return this;
    }

    private  BigInteger findE(BigInteger phi){
        BigInteger e = new BigInteger("65537");
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.ONE);
        }
        return e;
    }

    public RSA cypherText(byte[] text){
        BigInteger value = new BigInteger(text);
        BigInteger cypher = new BigInteger(String.valueOf(value)).modPow(publicKey.get(0), publicKey.get(1));
        setCypher(cypher.toByteArray());
        return this;
    }



    public byte[] decypherText(){
        BigInteger value = new BigInteger(cypher);
        BigInteger decypher = value.modPow(privateKey.get(0), privateKey.get(1));
        return decypher.toByteArray();
    }

    public byte[] getCypheredText() {
        return cypher;
    }

    public List<BigInteger> getPublicKey() {
        return publicKey;
    }

    

    public RSA setPublicKey(List<BigInteger> publicKey) {
        this.publicKey = publicKey;
        return this;

    }

    public RSA setPrivateKey(List<BigInteger> privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public RSA setCypher(byte[] cypher) {
        this.cypher = cypher;
        return this;
    }

    public List<BigInteger> getPrivateKey() {
        return privateKey;
    }

    
}
