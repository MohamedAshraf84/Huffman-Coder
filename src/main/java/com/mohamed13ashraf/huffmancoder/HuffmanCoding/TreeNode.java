package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import java.io.Serializable;

public class TreeNode implements Comparable<TreeNode>, Serializable {
    private final byte character;
    private final int frequency;
    private final TreeNode left;
    private final TreeNode right;

    public TreeNode(byte character, int frequency, TreeNode left, TreeNode right)
    {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public TreeNode getLeft()
    {
        return left;
    }

    public TreeNode getRight()
    {
        return right;
    }
    public byte getCharacter()
    {
        return character;
    }
    public int getFrequency()
    {
        return frequency;
    }

    @Override
    public int compareTo(TreeNode that)
    {
        return this.frequency - that.frequency;
    }

    public boolean isLeaf()
    {
        return this.left == null && this.right == null;
    }

    @Override
    public String toString()
    {
        return String.format("|%c : %d|" ,this.character , this.frequency);
    }

}
