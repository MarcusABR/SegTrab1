package br.com.segcomp.aes.key;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.tables.SBox;

public class AES128KeyScheduler implements KeyScheduler{

    private final Block[] expandedKey;

    private final int WORD_SIZE = 4;
    private final int INDEX_LAST_WORD = 12;

    private static final byte[] roundKeys = {0x01,0x02,0x04,0x08,0x10,0x20,0x40,(byte) 0x80,0x1B,0x36};

    public AES128KeyScheduler() {
        int expandedKeySize = 11;
        this.expandedKey = new Block[expandedKeySize];
        for(int i = 0; i < expandedKeySize; i++) {
            int KEY_LENGTH = 16;
            expandedKey[i] = new Block(new byte[KEY_LENGTH]);
        }
    }

    private void copyInitialKey(Key key) {
        expandedKey[0] = key.getBlock();
    }

    private void rotateWord(byte[] word) {
        byte aux = word[0];
        for(int i = 0; i < WORD_SIZE-1; i++){
            word[i] = word[i+1];
        }
        word[WORD_SIZE-1] = aux;
        //System.out.println(new Block(word));
    }

    private void subWord(byte[] word) {
        SBox sbox = SBox.getInstance();
        for(int i = 0; i < WORD_SIZE; i++){
            word[i] = sbox.substituteByte(word[i]);
        }
        //System.out.println(new Block(word));
    }

    private void roundConst(byte[] word, int round) {
        word[0] = (byte) (word[0] ^ roundKeys[round]);
        //System.out.println(new Block(word));
    }

    private byte[] xorWords(byte[] word1, byte[] word2) {
        byte[] result = new byte[WORD_SIZE];
        for(int i = 0; i < WORD_SIZE; i++) {
            result[i] = (byte) (word1[i] ^ word2[i]);
        }
        return result;
    }

    @Override
    public Block[] schedule(Key key) throws Exception {
        copyInitialKey(key);
        int rounds = 10;
        for (int i = 0; i < rounds; i++) {
            byte[] word = expandedKey[i].getWord(12, WORD_SIZE);
            rotateWord(word);
            subWord(word);
            roundConst(word, i);
            expandedKey[i+1].setWord(xorWords(expandedKey[i].getWord(0, WORD_SIZE), word), 0, WORD_SIZE);
            expandedKey[i+1].setWord(xorWords(expandedKey[i+1].getWord(0, WORD_SIZE), expandedKey[i].getWord(4, WORD_SIZE)), 4, WORD_SIZE);
            expandedKey[i+1].setWord(xorWords(expandedKey[i+1].getWord(4, WORD_SIZE),expandedKey[i].getWord(8, WORD_SIZE)), 8, WORD_SIZE);
            expandedKey[i+1].setWord(xorWords(expandedKey[i+1].getWord(8, WORD_SIZE),expandedKey[i].getWord(12, WORD_SIZE)), 12, WORD_SIZE);
        }
        return expandedKey;
    }
}
