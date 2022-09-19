package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.enums.KeyLength;
import br.com.segcomp.aes.key.AES128KeyScheduler;
import br.com.segcomp.aes.key.Key;

public class AES128 extends AdvancedEncryptionStandard {

    private final int wordSize = 4;

    private AES128Encryptor encryptor;
    private AES128Decryptor decryptor;

    public AES128() throws Exception {
        this.keyLength = KeyLength.KEY128;
        this.key = new Key(keyLength.getLengthInBytes());
        this.expandedKey = new AES128KeyScheduler().schedule(key);
        this.encryptor = new AES128Encryptor();
        this.decryptor = new AES128Decryptor();
    }

    @Override
    void encrypt(Block block) {
        encryptor.preRound(block, expandedKey[0]);
        for(int i = 1; i < 10; i++){
            encryptor.round(block, expandedKey[i]);
        }
        encryptor.lastRound(block, expandedKey[10]);
    }

    @Override
    void decrypt(Block block) {
        decryptor.preRound(block, expandedKey[10]);
        for(int i = 9; i > 0; i--){
            decryptor.round(block, expandedKey[i]);
        }
        decryptor.lastRound(block, expandedKey[0]);
    }

}
