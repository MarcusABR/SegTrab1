package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.enums.BlockSize;
import br.com.segcomp.aes.enums.KeyLength;
import br.com.segcomp.aes.enums.NumberOfRounds;
import br.com.segcomp.aes.key.AES128KeyScheduler;
import br.com.segcomp.aes.key.Key;

public class AES128 extends AdvancedEncryptionStandard {

    public AES128() throws Exception {
        this.keyLength = KeyLength.KEY128;
        this.blockSize = BlockSize.FOUR;
        this.numberOfRounds = NumberOfRounds.TEN;
        this.key = new Key(keyLength.getLengthInBytes());
        this.expandedKey = new AES128KeyScheduler().schedule(key);
    }

    private void shiftRow(byte[] block, int row, int shifts) {
        int col = (row-1)*4;
        for(int i = 0; i < shifts; i++) {
            byte aux = block[col];
            for(int j = 0; j < 3; j++) {
                block[j+col] = block[j+col+1];
            }
            block[col+3] = aux;
        }
    }

    @Override
    void shiftRows(Block block) {
        byte[] shifted = block.getBlock();
        shiftRow(shifted, 2, 1);
        shiftRow(shifted, 3, 2);
        shiftRow(shifted, 4, 3);
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

    @Override
    void mixColumns(Block block) {
        byte[] mixed = new byte[blockSize.getBlockSize()/8];
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

}
