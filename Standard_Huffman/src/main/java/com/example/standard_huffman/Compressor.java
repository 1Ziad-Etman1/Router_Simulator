package com.example.standard_huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//todo 1100111100101010011011110110010010

// Class for Huffman compression
public class Compressor {

    private Map<Character, String> encodingMap = new HashMap<>();

    public String compress(String input) {
        Map<Character, Integer> frequencyMap = buildFrequencyMap(input);
        PriorityQueue<HuffmanNode> priorityQueue = buildPriorityQueue(frequencyMap);
        HuffmanNode root = buildHuffmanTree(priorityQueue);
        buildEncodingMap(root, "");

        StringBuilder compressed = new StringBuilder();

        // Append codebook to the compressed string
        for (Map.Entry<Character, String> entry : encodingMap.entrySet()) {
            compressed.append(charToBinaryAscii(entry.getKey())).append(entry.getValue()).append('\n');
        }

        // Append compressed data to the string
        for (char c : input.toCharArray()) {
            compressed.append(encodingMap.get(c));
        }

        return compressed.toString();
    }

    public String charToBinaryAscii(char character) {
        // Convert the character to its ASCII value
        int asciiValue = (int) character;

        // Convert the ASCII value to an 8-bit binary string
        String binaryString = Integer.toBinaryString(asciiValue);

        // Pad with leading zeros if needed to make it 8 bits
        while (binaryString.length() < 8) {
            binaryString = "0" + binaryString;
        }

        return binaryString;
    }

    private Map<Character, Integer> buildFrequencyMap(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    private PriorityQueue<HuffmanNode> buildPriorityQueue(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        return priorityQueue;
    }

    private HuffmanNode buildHuffmanTree(PriorityQueue<HuffmanNode> priorityQueue) {
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    private void buildEncodingMap(HuffmanNode node, String code) {
        if (node == null) {
            return;
        }

        if (node.character != '\0') {
            encodingMap.put(node.character, code);
        }

        buildEncodingMap(node.left, code + "0");
        buildEncodingMap(node.right, code + "1");
    }

    public HuffmanNode getRootOfHuffmanTree() {
        return buildHuffmanTree(buildPriorityQueue(buildFrequencyMap("")));
    }
}
