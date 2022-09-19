package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.tables.SBox;

public class AES128Encryptor {

    private void subBytes(Block block){
        SBox sbox = SBox.getInstance();
        int byteAmount = block.getSize();
        byte[] originalBlock = block.getBlock();
        byte[] subbedBlock = new byte[byteAmount];
        for(int i = 0; i < byteAmount; i++){
            subbedBlock[i] = sbox.substituteByte(originalBlock[i]);
        }
        block.setBlock(subbedBlock);
    }

    private void shiftRow(byte[] block, int row, int shifts) {
        int col = row * 4;
        for(int i = 0; i < shifts; i++) {
            byte aux = block[col];
            for(int j = col; j < col + 3; j++) {
                block[j] = block[j+1];
            }
            block[col + 3] = aux;
        }
    }

    private void shiftRows(Block block) {
        byte[] shifted = block.getBlock();
        for(int i = 1; i < 4; i++) {
            shiftRow(shifted, i, i);
        }
        block.setBlock(shifted);
    }

    private void shiftPolynomial(int[] polynomial) {
        int aux = polynomial[0];
        for(int i = 3; i >= 0; i--) {
            polynomial[(i+1)%4] = polynomial[i];
        }
        polynomial[1] = aux;
    }

    private byte multiplyGF(byte multiplicand, int factor) {
        byte result = multiplicand;
        boolean upper1 = false;
        switch(factor) {
            case 1:
                break;
            case 2:
                upper1 = (0x80 & result) == 0x80;
                result = (byte) (result << 1);
                //System.out.println(Integer.toHexString(result));
                if (upper1){
                    result = (byte) (result ^ 0x1B);
                }
                break;
            case 3:
                result = (byte) (multiplyGF(result, 2) ^ result);
                break;
        }
        return result;
    }

    private void mixColumns(Block block) {
        byte[] mixed = new byte[block.getSize()];
        byte[] originalBlock = block.getBlock();
        int[] polynomial = {2, 3, 1, 1};
        for(int i = 0; i < 16; i++) {
            byte val = 0;
            for(int j = 0; j < 4; j++) {
                int index = ((j * 4) + (i % 4));
                if (val == 0) {
                    val = multiplyGF(originalBlock[index], polynomial[j]);
                } else {
                    val = (byte) (val ^ multiplyGF(originalBlock[index], polynomial[j]));
                }
            }
            mixed[i] = val;
            if(i%4 == 3) shiftPolynomial(polynomial);
        }
        block.setBlock(mixed);
    }

    private void addRoundKey(Block block, Block key) {
        byte[] blockBytes = block.getBlock();
        byte[] keyBytes = key.getBlock();
        for(int i = 0; i < block.getSize(); i++) {
            blockBytes[i] = (byte) (blockBytes[i] ^ keyBytes[i]);
        }
        block.setBlock(blockBytes);
    }

    protected void preRound(Block block, Block key){
        addRoundKey(block, key);
    }

    protected void round(Block block, Block key) {
        subBytes(block);
        shiftRows(block);
        mixColumns(block);
        addRoundKey(block, key);
    }

    protected void lastRound(Block block, Block key) {
        subBytes(block);
        shiftRows(block);
        addRoundKey(block, key);
    }
}
