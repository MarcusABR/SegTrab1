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
            //Inserir mensagem a ser criptografa no RSA aq
            String message = "AsajhvdaygusfasacasdasfascaAASDASASDACS";
            
            byte[] code = generateHash(message.getBytes());
            System.out.println("\n");
            System.out.println("Hash da mensagem: "+oaep.turnToHexcode(code));
            byte[] x = Base64.getEncoder().encode(code);
            rsa.cypherText(code);
            System.out.println("\nMensagem cifrada: "+oaep.turnToHexcode(rsa.getCypheredText()));
            System.out.println("\nMensagem decifrada: "+oaep.turnToHexcode(rsa.decypherText()));

            System.out.println("---------------------------------------OAEP---------------------------------------");
            String label = "Teste";
            System.out.println("\nHash da label: "+oaep.turnToHexcode(generateHash(label.getBytes())));
            byte[] res = rsa.getOaep().padding(message.getBytes(), label);
            System.out.println("\nPadding: "+oaep.turnToHexcode(res));
            rsa.cypherText(res);
            System.out.println("\nMensagem cifrada com RSA: "+oaep.turnToHexcode(rsa.getCypheredText()));
            System.out.println("\nMensagem decifrada: "+oaep.turnToHexcode(rsa.decypherText()));
            List<byte[]> depadding = rsa.getOaep().depadding(rsa.decypherText(), message.getBytes().length);
            System.out.println("\nMensagem decifrada: "+oaep.turnToHexcode(depadding.get(0)));
            System.out.println("\nHash da label decifrada: "+oaep.turnToHexcode(depadding.get(0)));


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
