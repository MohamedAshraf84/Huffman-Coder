package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import com.mohamed13ashraf.huffmancoder.MyDataStructures.AVLMap;

public class HuffmanDecoder {

    private final TreeNode huffmanTree;

    private final AVLMap<String, Byte> originalBytes;


    public HuffmanDecoder(TreeNode huffmanTree) {
        this.huffmanTree = huffmanTree;
        this.originalBytes = new AVLMap<>();
    }

    public AVLMap<String, Byte> decode()
    {
        if (huffmanTree == null)
            return originalBytes;

        getOriginalCodes(huffmanTree);

        return originalBytes;
    }

    public void getOriginalCodes(TreeNode root)
    {
        preOrderTraversal(root, new StringBuilder());
    }

    public void preOrderTraversal(TreeNode root, StringBuilder code)
    {
        if (root.isLeaf())
        {
            originalBytes.put(code.toString(), root.getCharacter());
            return;
        }

        preOrderTraversal(root.getLeft(), new StringBuilder(code).append("0"));
        preOrderTraversal(root.getRight(), new StringBuilder(code).append("1"));
    }
}
