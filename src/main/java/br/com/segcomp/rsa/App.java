package br.com.segcomp.rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String message =  "a";

        BigInteger p = BigInteger.probablePrime(1024, new Random());
        BigInteger q = BigInteger.probablePrime(1024, new Random());
        

        System.out.println(p.toString());
        System.out.println(q.toString());
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = calculateE(phi);
        System.out.println("AChou "+e.toString());


        // find E wich is coprime with n and phi, being less tha phi
        // Thats the public key



        // d.e mod phi(n) = 1, find d


    }

    static BigInteger calculateE(BigInteger phi){
        BigInteger i = BigInteger.valueOf(2);
        while ( i.gcd(phi).compareTo(BigInteger.ONE)!=0){
            i.add(BigInteger.ONE);
        }
        return i;
    }

    
}
