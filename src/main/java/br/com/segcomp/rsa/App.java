package br.com.segcomp.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App {
    public static void main(String[] args) {
        
        try {
            MessageDigest dg = MessageDigest.getInstance("SHA-256");
            OAEP oaep = new OAEP(dg, new MGF1(dg), new SecureRandom());
            RSA rsa = new RSA(new PrimeGenerator(new SecureRandom()), oaep, new SecureRandom());
            rsa.createKeys();
            String message = "ABCasdasdasdasdasdashggygacdascascascasczasujhduasgyhascasASCASCADcsasdasdBbB";
            
            byte[] code = generateHash(message.getBytes());
            byte[] x = Base64.getEncoder().encode(code);
            System.out.println(oaep.turnToHexcode(code));
            rsa.cypherText(code);
            System.out.println(oaep.turnToHexcode(rsa.decypherText()));


            byte[] res = rsa.getOaep().padding(code, "aiai");
            List<byte[]> depadding = rsa.getOaep().depadding(res, code.length);

        } catch (NoSuchAlgorithmException | IOException e) {
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
