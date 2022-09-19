package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;

import java.io.File;
import java.io.FileInputStream;

public class App {

    public static void main(String[] args) throws Exception {
        AdvancedEncryptionStandard aes = new AES128CTR();
        byte[] bytes = {0x00, 0x01, 0x02, 0x02, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        File file = new File("Linux_logo.jpg");
        byte[] array;
        try (FileInputStream fileStream = new FileInputStream(file)) {
            array = new byte[(int)file.length()];
            int bytesRead = fileStream.read(array);
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Block block = new Block(bytes);
        System.out.println(block);
        aes.encrypt(block);
        System.out.println(block);
        aes.decrypt(block);
        System.out.println(block);
    }
}
