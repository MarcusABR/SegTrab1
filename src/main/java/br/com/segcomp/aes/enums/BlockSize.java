package br.com.segcomp.aes.enums;

public enum BlockSize {
    FOUR(4), SIX(6), EIGHT(8);

    private int blocks;

    BlockSize(int blocks) {
        this.blocks = blocks;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getBlockSize() {
        return blocks * 32;
    }
}
