package br.com.segcomp.aes.enums;

public enum KeyLength {
    KEY128(128), KEY192(192), KEY256(256);

    private int bits;

    KeyLength(int value){
        this.bits = value;
    }

    public int getLengthInBits() {
        return bits;
    }

    public int getLengthInBytes() { return bits/8; }
}
