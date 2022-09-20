package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.io.IO;

import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) throws Exception {
        IO io = new IO();
        AES128CTR AES = new AES128CTR();
        byte[] bytes = {0x00, 0x01, 0x02, 0x02, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};
        String string = "O rato roeu a roupa do rei de roma rapazzzzzz";
        bytes = string.getBytes(Charset.defaultCharset());
        String filename = "Linux_logo.jpg";
        //Block[] stream = Block.createBlockArray(bytes, 16);
        Block[] stream = io.openFileFromResources("Linux_logo.jpg").get();
        /**
         * System.out.println(block);
         *         aes.encrypt(block);
         *         System.out.println(block);
         *         aes.decrypt(block);
         *         System.out.println(block);
         */
        for(Block b : stream) {
            System.out.println(b);
        }
        System.out.println(" ");
        AES.encryptStream(stream);
        /**for(Block b : stream) {
            System.out.println(b);
        }**/
        System.out.println(" ");
        AES.decryptStream(stream);
        /**for(Block b : stream) {
            System.out.println(b);
        }**/
        System.out.println(" ");
        byte[] decrypted = Block.getByteArray(stream);
        io.writeFileToResources(decrypted, ".jpg");
        //String decryptedString = new String(decrypted, Charset.defaultCharset());
        //System.out.println(decryptedString);
    }
}
