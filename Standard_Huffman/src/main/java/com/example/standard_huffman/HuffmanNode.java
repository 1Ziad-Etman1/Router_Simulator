package com.example.standard_huffman;

// Class representing a node in the Huffman tree
class HuffmanNode implements Comparable<HuffmanNode> {
    char character;
    int frequency;
    HuffmanNode left, right;

    // Constructor to initialize a HuffmanNode with a character and its frequency
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    // Comparable implementation to compare nodes based on frequency
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}
