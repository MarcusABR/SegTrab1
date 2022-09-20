package br.com.segcomp;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import br.com.segcomp.aes.AES128CTR;
import br.com.segcomp.aes.block.Block;
import br.com.segcomp.io.IO;
import br.com.segcomp.rsa.*;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {

        try {
            MessageDigest dg = MessageDigest.getInstance("SHA-256");
            OAEP oaep = new OAEP(dg, new MGF1(dg), new SecureRandom());
            RSA rsa = new RSA(new PrimeGenerator(new SecureRandom()), oaep, new SecureRandom());

            rsa.createKeys();
            String message = "AsajhvdaygusfasacasdasfascaAASDASASDACS";

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
        // Calcular o hash
        // Criptografar o hash com RSA
        // Gerar a chave do AES
        StringBuilder stringBuilder = new StringBuilder();
        AES128CTR AES = new AES128CTR();
        stringBuilder.append("Chave utilizada: ");
        stringBuilder.append(AES.getKey().getKey().toString()).append("%n");

        // Usar a chave do AES para criptografar o arquivo
        IO io = new IO();
        Block[] blocks = io.openFileFromResources("Message.txt").orElse(null);
        AES.encryptStream(blocks);
        io.writeFileToResources(stringBuilder.toString().getBytes(), ".txt");
        Thread.sleep(1000);
        io.writeFileToResources(Block.getByteArray(blocks), ".txt");
        AES.decryptStream(blocks);
        io.writeFileToResources(Block.getByteArray(blocks), ".txt");
        // Usar o RSA para criptografar a chave do AES
        // Usar o RSA para descriptografar a chave do AES
        //-----> usa essa linha pra setar a chave descriptografada: AES.setKey( );
        // Usar o RSA pra descriptografar a chave e o hash
        // Descriptografar o arquivo

    public static byte[] generateHash(byte[] info) throws NoSuchAlgorithmException{
        MessageDigest dg = MessageDigest.getInstance("SHA-256");
        byte[] code = dg.digest(info);
        return code;
    }
}
