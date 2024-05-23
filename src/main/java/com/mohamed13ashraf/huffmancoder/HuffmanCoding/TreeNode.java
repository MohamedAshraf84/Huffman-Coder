package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import java.io.Serializable;

public record TreeNode(byte character, int frequency, TreeNode left, TreeNode right)
        implements Comparable<TreeNode>, Serializable {

    @Override
    public int compareTo(TreeNode that) {
        return this.frequency - that.frequency;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}
