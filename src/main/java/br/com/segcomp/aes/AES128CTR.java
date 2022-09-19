package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.enums.KeyLength;
import br.com.segcomp.aes.key.AES128KeyScheduler;
import br.com.segcomp.aes.key.Key;

import java.math.BigInteger;
import java.security.SecureRandom;

public class AES128CTR extends AdvancedEncryptionStandard {

    private final int wordSize = 4;

    private AES128Encryptor encryptor;
    private AES128Decryptor decryptor;

    private byte[] counter;

    public AES128CTR() throws Exception {
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

    void encryptStream(Block[] blocks) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] counterStart = new byte[16];
        secureRandom.nextBytes(counterStart);
        this.counter = counterStart;
        BigInteger counter = new BigInteger(counterStart);
        for (Block b : blocks) {
            Block initializationVector = new Block(counter.toByteArray());
            encrypt(initializationVector);
            encryptor.addRoundKey(b, initializationVector);
            counter = counter.add(BigInteger.ONE);
        }
    }

    void decryptStream(Block[] blocks) {
        BigInteger counter = new BigInteger(this.counter);
        for (Block b : blocks) {
            Block initializationVector = new Block(counter.toByteArray());
            encrypt(initializationVector);
            decryptor.addRoundKey(b, initializationVector);
            counter = counter.add(BigInteger.ONE);
        }
    }
}
