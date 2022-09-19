package br.com.segcomp.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App {
    public static void main(String[] args) {
        
        try {
            MessageDigest dg = MessageDigest.getInstance("SHA-256");
            OAEP oaep = new OAEP(dg, new MGF1(dg), new SecureRandom());
            RSA rsa = new RSA(new PrimeGenerator(new SecureRandom()), oaep, new SecureRandom());
            rsa.createKeys();
            String message = "ABCDE";
            byte[] code = generateHash(message.getBytes());
            System.out.println("Cifrada "+oaep.turnToHexcode(code));
            rsa.cypherText(code);
            System.out.println("Decifrada "+oaep.turnToHexcode(rsa.decypherText())+"  - ");




        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        
    }

    public static byte[] generateHash(byte[] info) throws NoSuchAlgorithmException{
        MessageDigest dg = MessageDigest.getInstance("SHA-256");
        byte[] code = dg.digest(info);
        return code;
    }

  

    

    
}
