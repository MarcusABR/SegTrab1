package br.com.segcomp.aes;

import br.com.segcomp.aes.block.Block;
import br.com.segcomp.aes.enums.BlockSize;
import br.com.segcomp.aes.enums.KeyLength;
import br.com.segcomp.aes.enums.NumberOfRounds;
import br.com.segcomp.aes.key.Key;
import br.com.segcomp.aes.sbox.SBox;

abstract class AdvancedEncryptionStandard {

    KeyLength keyLength;
    BlockSize blockSize;
    NumberOfRounds numberOfRounds;
    Key key;

    Block[] expandedKey;

    public void subBytes(Block block){
        SBox sbox = SBox.getInstance();
        int byteAmount = blockSize.getBlockSize()/8;
        byte[] originalBlock = block.getBlock();
        byte[] subbedBlock = new byte[blockSize.getBlockSize()/8];
        for(int i = 0; i < byteAmount; i++){
            subbedBlock[i] = sbox.substituteByte(originalBlock[i]);
        }
        block.setBlock(subbedBlock);
    }

    abstract void shiftRows(Block block);
    abstract void mixColumns(Block block);

    public void transform(Block block) {
        System.out.println(block);
        subBytes(block);
        System.out.println(block);
        shiftRows(block);
        System.out.println(block);
        mixColumns(block);
        System.out.println(block);
    }
}
