package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import com.mohamed13ashraf.huffmancoder.MyDataStructures.AVLMap;
import com.mohamed13ashraf.huffmancoder.MyDataStructures.SinglyLinkedList;
import com.mohamed13ashraf.huffmancoder.MyDataStructures.MinimumHeap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HuffmanEncoder {
    private final FileInputStream fileReader;
    private final SinglyLinkedList<Byte> messageCharacters;
    private final AVLMap<Byte, Integer> charsFrequency;
    private final MinimumHeap<TreeNode> charsFrequencyPQ;
    private final TreeNode huffmanTree;
    private final AVLMap<Byte, String> huffmanCodes;

    public HuffmanEncoder(File source) {
        try {
            fileReader = new FileInputStream(source);
            messageCharacters = this.readCharactersFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        charsFrequency = this.countCharactersFrequencies();
        charsFrequencyPQ = this.getCharactersPriorityQueue();
        huffmanTree = this.buildHuffmanTree();
        huffmanCodes = new AVLMap<>();
    }

    public TreeNode getHuffmanTree()
    {
        return huffmanTree;
    }

    public String encode()
    {
        generateHuffmanCodes();
        return encodeMessage();
    }

    private void generateHuffmanCodes()
    {
        preOrderTraversal(huffmanTree, new StringBuilder());
    }

    private void preOrderTraversal(TreeNode curNode, StringBuilder binCode)
    {
        if (curNode.isLeaf())
        {
            huffmanCodes.put(curNode.character(), binCode.toString());
            return;
        }

        preOrderTraversal(curNode.left(), new StringBuilder(binCode).append("0"));
        preOrderTraversal(curNode.right(), new StringBuilder(binCode).append("1"));
    }

    private SinglyLinkedList<Byte> readCharactersFromFile() throws IOException {
        SinglyLinkedList<Byte> charactersSequence = new SinglyLinkedList<>();

        int n = fileReader.available();
        byte []oneByte;
        for (int i = 0; i < n; ++i) {
            oneByte = fileReader.readNBytes(1);
            charactersSequence.add(oneByte[0]);
        }

        return charactersSequence;
    }

    private AVLMap<Byte, Integer> countCharactersFrequencies()
    {
        AVLMap<Byte, Integer> charsFrequency = new AVLMap<>();

        for (byte aChar : messageCharacters)
        {
            if (charsFrequency.containsKey(aChar))
                charsFrequency.put(aChar, charsFrequency.get(aChar) + 1);
            else
                charsFrequency.put(aChar, 1);
        }

        return charsFrequency;
    }

    private MinimumHeap<TreeNode> getCharactersPriorityQueue()
    {
        MinimumHeap<TreeNode> charsFrequencyPQ = new MinimumHeap<>();
        for (AVLMap.AVLNode<Byte, Integer> ch : charsFrequency.entrySet()) {
            TreeNode node = new TreeNode(ch.getKey(), ch.getValue(), null, null);
            charsFrequencyPQ.add(node);
        }

        return charsFrequencyPQ;
    }

    private String encodeMessage() {
        StringBuilder encodedMessageBitSequence = new StringBuilder();

        for (byte aChar : messageCharacters)
            encodedMessageBitSequence.append(huffmanCodes.get(aChar));

        return encodedMessageBitSequence.toString();
    }

    private TreeNode mergeTwoTrees(TreeNode left, TreeNode right)
    {
        return new TreeNode((byte) '\0', left.frequency() + right.frequency(), left, right);
    }

    private TreeNode buildHuffmanTree()
    {
        while (charsFrequencyPQ.size() > 1)
        {
            TreeNode left = charsFrequencyPQ.poll();
            TreeNode right = charsFrequencyPQ.poll();
            charsFrequencyPQ.add(mergeTwoTrees(left, right));
        }

        return charsFrequencyPQ.poll();
    }

    public AVLMap<Byte, Integer> getFrequencyTable()
    {
        return charsFrequency;
    }

    public AVLMap<Byte, String> getHuffmanCodes()
    {
        return huffmanCodes;
    }
}
