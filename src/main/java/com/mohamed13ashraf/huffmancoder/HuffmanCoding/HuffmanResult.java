package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import java.io.Serializable;
import java.util.BitSet;

public class HuffmanResult implements Serializable {
    private final int encodedMessageLength;
    private final BitSet encodedMessage;
    private final TreeNode huffmanTree;

    public HuffmanResult(int encodedMessageLength, BitSet encodedMessage, TreeNode huffmanTree)
    {
        this.encodedMessageLength = encodedMessageLength;
        this.encodedMessage = encodedMessage;
        this.huffmanTree = huffmanTree;
    }

    public TreeNode getHuffmanTree()
    {
        return huffmanTree;
    }

    public int getEncodedMessageLength() {
       return encodedMessageLength;
    }

    public BitSet getEncodedMessage() {
        return encodedMessage;
    }
}
