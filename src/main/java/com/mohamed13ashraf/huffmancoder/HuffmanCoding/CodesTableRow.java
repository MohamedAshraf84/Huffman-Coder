package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

public class CodesTableRow {

    private char character;
    private String frequency;
    private String bitSequence;

    public CodesTableRow(char character, String frequency, String bitSequence) {
        this.character = character;
        this.frequency = frequency;
        this.bitSequence = bitSequence;
    }

    public char getCharacter() {
        return character;
    }
    public void setCharacter(char character) {
        this.character = character;
    }

    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getBitSequence() {
        return bitSequence;
    }
    public void setBitSequence(String bitSequence) {
        this.bitSequence = bitSequence;
    }

}