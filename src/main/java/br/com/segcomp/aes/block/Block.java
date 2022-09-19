package br.com.segcomp.aes.block;

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
