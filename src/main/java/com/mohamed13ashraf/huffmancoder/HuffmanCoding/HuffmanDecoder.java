package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import com.mohamed13ashraf.huffmancoder.MyDataStructures.AVLMap;
import java.util.BitSet;

public class HuffmanDecoder {

    private final int encodedMessageLength;
    private final BitSet encodedMessageBitSet;
    private final TreeNode huffmanTree;
    private final AVLMap<String, Byte> byteCodes;

    public HuffmanDecoder(HuffmanResult huffmanResult) {
        this.encodedMessageLength = huffmanResult.encodedMessageLength();
        this.encodedMessageBitSet = huffmanResult.encodedMessage();
        this.huffmanTree = huffmanResult.huffmanTree();
        this.byteCodes = new AVLMap<>();
    }

    public String decode()
    {
        if (huffmanTree == null)
            return "";

        generateByteCodes();
        return decodeMessage(convertBitSetToString());
    }

    public void generateByteCodes()
    {
        preOrderTraversal(huffmanTree, new StringBuilder());
    }

    private String convertBitSetToString() {
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < encodedMessageLength; ++i) {
            encodedMessage.append(encodedMessageBitSet.get(i) ? "1" : "0");
        }
        return encodedMessage.toString();
    }

    private String decodeMessage(String encodedMessage) {
        StringBuilder message = new StringBuilder();
        int currentLength = 1;
        int start = 0;

        while (start < encodedMessageLength) {
            String key = encodedMessage.substring(start, start + currentLength);
            if (byteCodes.containsKey(key)) {
                byte ch = byteCodes.get(key);
                message.append((char) ch);
                start += currentLength;
                currentLength = 1;
                continue;
            }
            currentLength++;
        }

        return message.toString();
    }

    public void preOrderTraversal(TreeNode root, StringBuilder code)
    {
        if (root.isLeaf())
        {
            byteCodes.put(code.toString(), root.character());
            return;
        }

        preOrderTraversal(root.left(), new StringBuilder(code).append("0"));
        preOrderTraversal(root.right(), new StringBuilder(code).append("1"));
    }
}
