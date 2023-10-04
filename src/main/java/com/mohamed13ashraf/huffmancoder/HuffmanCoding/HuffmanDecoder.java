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
        InOrderTraversal(root, new StringBuilder());
    }

    public void InOrderTraversal(TreeNode root, StringBuilder code)
    {
        if (root.isLeaf())
        {
            originalBytes.put(code.toString(), root.getCharacter());
            if (code.length() != 0)
                code.deleteCharAt(code.length() - 1);
            return;
        }

        InOrderTraversal(root.getLeft(), code.append("0"));
        InOrderTraversal(root.getRight(), code.append("1"));

        if (code.length() != 0)
            code.deleteCharAt(code.length() - 1);
    }
}
