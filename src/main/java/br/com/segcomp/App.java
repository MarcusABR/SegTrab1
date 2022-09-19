package br.com.segcomp;

import br.com.segcomp.aes.AES128CTR;
import br.com.segcomp.aes.block.Block;
import br.com.segcomp.io.IO;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
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

        // Calcula o hash do arquivo
        // Descriptografa o hash recebido
        // Compara o hash recebido com o calculado
    }
}
