package br.com.segcomp.aes.block;

import java.util.Arrays;

public class Block {

    private byte[] block;

    public Block(byte[] block) {
        this.block = block;
    }

    public byte[] getBlock() {
        return block;
    }

    public void setBlock(byte[] block) {
        this.block = block;
    }

    public byte[] getWord(int startIndex, int wordSize) throws Exception {
        if (startIndex%4 > 0) {
            throw new Exception("Invalid starting index");
        }
        byte[] word = new byte[wordSize];
        System.arraycopy(block, startIndex, word, 0, wordSize);
        return word;
    }

    public void setWord(byte[] word, int startIndex, int wordSize) {
        if (wordSize >= 0) System.arraycopy(word, 0, block, startIndex, wordSize);
    }

    public int getSize() {
        return block.length;
    }
    public static Block[] createBlockArray(byte[] block, int bytesPerBlock) {
        int blocks = (int) Math.ceil((double)block.length/bytesPerBlock);
        block = Arrays.copyOf(block, blocks*16);
        Block[] blockArray = new Block[blocks];
        for(int i = 0; i < blockArray.length; i++) {
            blockArray[i] = new Block(new byte[bytesPerBlock]);
            System.arraycopy(block, i*16, blockArray[i].block, 0, 16);
        }
        return blockArray;
    }

    public static byte[] getByteArray(Block[] blocks) {
        byte[] array = new byte[blocks.length*blocks[0].getSize()];
        for(int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i].block, 0, array, i*16, 16);
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        for (byte b : block) {
            stringBuilder.append(String.format("%02x ", b));
        }
        return stringBuilder.toString();
    }
}
