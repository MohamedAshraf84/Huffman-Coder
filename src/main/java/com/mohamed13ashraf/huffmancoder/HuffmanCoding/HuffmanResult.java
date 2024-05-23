package com.mohamed13ashraf.huffmancoder.HuffmanCoding;

import java.io.Serializable;
import java.util.BitSet;

public record HuffmanResult(int encodedMessageLength, BitSet encodedMessage,
                            TreeNode huffmanTree) implements Serializable {
}
