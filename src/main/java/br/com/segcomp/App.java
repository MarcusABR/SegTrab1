package br.com.segcomp;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import br.com.segcomp.aes.AES128CTR;
import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.key.Key;
import br.com.segcomp.io.IO;
import br.com.segcomp.rsa.*;

public class App {
    public static void main(String[] args) throws Exception {

        try {
            // Gerando a chave do AES
            System.out.println("Instanciando AES e gerando chave de 128 bits...");
            AES128CTR aes = new AES128CTR();
            String string = "\nChave gerada para AES: " +
                    aes.getKey().getBlock().toString();
            System.out.println(string);
            byte[] aesKey = aes.getKey().getBlock().getBlock();
            System.out.println("\nChave expandida do AES: ");
            for(Block b : aes.getExpandedKey()) {
                System.out.println(b.toString());
            }

            // Criando mensagem
            System.out.println("\nCriando mensagem RSA...");
            MessageDigest dg = MessageDigest.getInstance("SHA-256");
            OAEP oaep = new OAEP(dg, new MGF1(dg), new SecureRandom());
            RSA rsa = new RSA(new PrimeGenerator(new SecureRandom()), oaep, new SecureRandom());
            RSA rsaAesKey = new RSA(new PrimeGenerator(new SecureRandom()), oaep, new SecureRandom());
            rsa.createKeys();
            rsaAesKey.createKeys();
            // Inserir mensagem a ser criptografa no RSA aqui
            // String message = "AsajhvdaygusfasacasdasfascaAASDASASDACS";

            System.out.println("\nAbrindo o arquivo de texto...");
            IO io = new IO();
            Block[] blocks = io.openFileFromResources("Message.txt").orElse(null);
            System.out.println("\nTexto: ");
            System.out.println(new String(Block.getByteArray(blocks)));
            System.out.println("\nCifrando arquivo de texto com AES...");
            aes.encryptStream(blocks);
            System.out.println("\nTexto cifrado: ");
            System.out.println(new String(Block.getByteArray(blocks)));

            // Gerando o hash do arquivo de texto cifrado
            System.out.println("\nGerando o hash do arquivo de texto cifrado...");
            byte[] code = generateHash(Block.getByteArray(blocks));
            System.out.println("\n");
            System.out.println("\nHash do arquivo: " + oaep.turnToHexcode(code));
            byte[] x = Base64.getEncoder().encode(code);

            System.out.println("\nCifrando o hash e chave com RSA...");
            rsa.cypherText(code);
            rsaAesKey.cypherText(aesKey);
            System.out.println("\nHash cifrado: " + oaep.turnToHexcode(rsa.getCypheredText()));
            System.out.println("\nChave cifrada: " + oaep.turnToHexcode(rsaAesKey.getCypheredText()));

            System.out.println("\nDecifrando o hash e chave com RSA...");
            System.out.println("\nHash decifrado: " + oaep.turnToHexcode(rsa.decypherText()));
            Key decypheredKey = new Key(new Block(rsaAesKey.decypherText()));
            System.out.println("\nChave decifrada: " + decypheredKey.getBlock());
            code = generateHash(Block.getByteArray(blocks));
            System.out.println("\n");
            System.out.println("\nHash do arquivo: " + oaep.turnToHexcode(code));

            System.out.println("\nDecifrando arquivo cifrado com AES...");
            aes.setKey(decypheredKey);
            aes.decryptStream(blocks);
            System.out.println("\nTexto decifrado:");
            System.out.println(new String(Block.getByteArray(blocks)));

            System.out.println("\n\n---------------------------------------OAEP---------------------------------------");
            String label = "Teste";
            System.out.println("\nHash da label: " + oaep.turnToHexcode(generateHash(label.getBytes())));
            System.out.println("\nChave AES: " + oaep.turnToHexcode(aesKey));
            byte[] res = rsa.getOaep().padding(aesKey, label);
            System.out.println("\nPadding: " + oaep.turnToHexcode(res));
            rsa.cypherText(res);
            System.out.println("\nMensagem cifrada com RSA: " + oaep.turnToHexcode(rsa.getCypheredText()));
            System.out.println("\nMensagem decifrada do RSA: " + oaep.turnToHexcode(rsa.decypherText()));
            List<byte[]> depadding = rsa.getOaep().depadding(rsa.decypherText(), aesKey.length);
            System.out.println("\nMensagem decifrada do OAEP (chave AES): " + oaep.turnToHexcode(depadding.get(1)));
            System.out.println("\nHash da label decifrada: " + oaep.turnToHexcode(depadding.get(0)));
        } catch (NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static byte[] generateHash(byte[] info) throws NoSuchAlgorithmException {
        MessageDigest dg = MessageDigest.getInstance("SHA-256");
        byte[] code = dg.digest(info);
        return code;
    }
}