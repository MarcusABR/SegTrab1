package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;

public class App {

    public static void main(String[] args) throws Exception {
        //byte[] bytes = {(byte) 0xEA,0x04,0x65, (byte) 0x85, (byte) 0x83,0x45,0x5D, (byte) 0x96,0x5C,0x33, (byte) 0x98, (byte) 0xB0, (byte) 0xF0,0x2D, (byte) 0xAD, (byte) 0xC5};
        //Block block = new Block(bytes);
        AdvancedEncryptionStandard aes = new AES128();
        //aes.transform(block);
    }
}
