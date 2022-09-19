package br.com.segcomp.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
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
            String message = "A";
            byte[] code = generateHash(message.getBytes());
            System.out.println("Cifrada "+oaep.turnToHexcode(code));
            rsa.cypherText(code);
            rsa.decypherText();

            System.out.println("entra+ "+oaep.turnToHexcode(code));
            byte[] res = rsa.getOaep().padding(code, "aiai");
            System.out.println(oaep.turnToHexcode(res));
            System.out.println(oaep.turnToHexcode(generateHash("aiai".getBytes())));
            List<byte[]> depadding = rsa.getOaep().depadding(res, code.length);
            System.out.println("res "+oaep.turnToHexcode(depadding.get(1)));
            System.out.println("res "+oaep.turnToHexcode(depadding.get(0)));

            // public static void main(String[] args) {
    //     MessageDigest dg;
    //     try {
    //         dg = MessageDigest.getInstance("SHA-256");
    //         OAEP oaep = new OAEP(dg, new MGF1(dg), new SecureRandom());
    //         byte[] asd = oaep.padding("ayuashudasdasdasdasdasdasdasdqda", "asdasdasdasdasdasd");
    //         String xuy = oaep.depadding(asd,);

    //     } catch (NoSuchAlgorithmException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     } catch (IOException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
        
    // }




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
