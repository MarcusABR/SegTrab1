package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.tables.MultiplyTablesGF;
import br.com.segcomp.aes.tables.SBox;

public class AES128Decryptor {

    private void invSubBytes(Block block){
        SBox sbox = SBox.getInstance();
        int byteAmount = block.getSize();
        byte[] originalBlock = block.getBlock();
        byte[] subbedBlock = new byte[byteAmount];
        for(int i = 0; i < byteAmount; i++){
            subbedBlock[i] = sbox.invSubstituteByte(originalBlock[i]);
        }
        block.setBlock(subbedBlock);
    }

    private void invShiftRow(byte[] block, int row, int shifts) {
        int col = row * 4;
        for(int i = 0; i < shifts; i++) {
            byte aux = block[col];
            for(int j = col; j < col + 3; j++) {
                block[j] = block[j+1];
            }
            block[col + 3] = aux;
        }
    }

    private void invShiftRows(Block block) {
        byte[] shifted = block.getBlock();
        invShiftRow(shifted, 1, 3);
        invShiftRow(shifted, 2, 2);
        invShiftRow(shifted, 3, 1);
        block.setBlock(shifted);
    }

    private void shiftPolynomial(int[] polynomial) {
        int aux = polynomial[0];
        for(int i = 3; i >= 0; i--) {
            polynomial[(i+1)%4] = polynomial[i];
        }
        polynomial[1] = aux;
    }

    private void invMixColumns(Block block) {
        MultiplyTablesGF multiplyTablesGF = MultiplyTablesGF.getInstance();
        byte[] mixed = new byte[block.getSize()];
        byte[] originalBlock = block.getBlock();
        int[] polynomial = {14, 11, 13, 9};
        for(int i = 0; i < 16; i++) {
            byte val = 0;
            for(int j = 0; j < 4; j++) {
                int index = ((j * 4) + (i % 4));
                if (val == 0) {
                    val = multiplyTablesGF.multiply(originalBlock[index], polynomial[j]);
                } else {
                    val = (byte) (val ^ multiplyTablesGF.multiply(originalBlock[index], polynomial[j]));
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
        invShiftRows(block);
        invSubBytes(block);
        addRoundKey(block, key);
        invMixColumns(block);
    }

    protected void lastRound(Block block, Block key) {
        invShiftRows(block);
        invSubBytes(block);
        addRoundKey(block, key);
    }
}
