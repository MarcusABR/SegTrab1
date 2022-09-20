package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.key.AES128KeyScheduler;
import br.com.segcomp.aes.key.Key;
import br.com.segcomp.io.IO;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class AES128CTR {

    Key key;
    Block[] expandedKey;

    private final int wordSize = 4;
    private final int keyLengthInBytes = 16;

    private final byte[] zero = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private AES128Encryptor encryptor;
    private AES128Decryptor decryptor;

    private byte[] counter;

    public AES128CTR() throws Exception {
        this.key = new Key(keyLengthInBytes);
        this.expandedKey = new AES128KeyScheduler().schedule(key);
        this.encryptor = new AES128Encryptor();
        this.decryptor = new AES128Decryptor();
    }

    public Key getKey() {
        return this.key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Block[] getExpandedKey() {
        return expandedKey;
    }

    public void encrypt(Block block) {
        encryptor.preRound(block, expandedKey[0]);
        for(int i = 1; i < 10; i++){
            encryptor.round(block, expandedKey[i]);
        }
        encryptor.lastRound(block, expandedKey[10]);
    }

    public void decrypt(Block block) {
        decryptor.preRound(block, expandedKey[10]);
        for(int i = 9; i > 0; i--){
            decryptor.round(block, expandedKey[i]);
        }
        decryptor.lastRound(block, expandedKey[0]);
    }

    public void encryptStream(Block[] blocks) throws FileNotFoundException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] counterStart = new byte[16];
        secureRandom.nextBytes(counterStart);
        this.counter = counterStart;
        IO io = new IO();
        BigInteger counter = new BigInteger(this.counter);
        for (Block b : blocks) {
            Block initializationVector = new Block(counter.toByteArray());
            encrypt(initializationVector);
            encryptor.addRoundKey(b, initializationVector);
            counter = counter.add(BigInteger.ONE);
        }
    }

    public void decryptStream(Block[] blocks) {
        BigInteger counter = new BigInteger(this.counter);
        //BigInteger counter = new BigInteger("0000000000000000", 16);
        for (Block b : blocks) {
            Block initializationVector = new Block(counter.toByteArray());
            encrypt(initializationVector);
            decryptor.addRoundKey(b, initializationVector);
            counter = counter.add(BigInteger.ONE);
        }
    }
}
