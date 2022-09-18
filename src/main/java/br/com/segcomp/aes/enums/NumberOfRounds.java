package br.com.segcomp.aes.enums;

public enum NumberOfRounds {
    TEN(10), TWELVE(12), FOURTEEN(14);

    private int numberOfRounds;

    NumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }
}
