package com.example.standard_huffman;

import java.io.*;
import java.util.BitSet;

// Class for handling file operations related to Huffman encoding and decoding
public class HuffmanFileHandler {

    // Method to compress a text file and save the compressed data to a binary file
    public static void compressToFile(String inputFileName, String outputFileName) {
        try {
            String input = readTextFile(inputFileName);
            Compressor compressor = new Compressor();
            String compressed = compressor.compress(input);
            BitSet bitSet = stringToBitSet(compressed);
            writeBitSetToFile(bitSet, outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to decompress a binary file and save the decompressed data to a text file
    public static void decompressToFile(String inputFileName, String outputFileName) {
        try {
            BitSet bitSet = readBitSetFromFile(inputFileName);
            String compressed = bitSetToString(bitSet);
            DeCompressor deCompressor = new DeCompressor();
            String decompressed = deCompressor.decompress(compressed);
            writeTextFile(decompressed, outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readTextFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
        }
        return content.toString();
    }

    private static void writeTextFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    public static BitSet readBitSetFromFile(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (BitSet) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new BitSet();
        }
    }

    private static void writeBitSetToFile(BitSet bitSet, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(bitSet);
        }
    }

    private static BitSet stringToBitSet(String input) {
        BitSet bitSet = new BitSet(input.length());
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public static String bitSetToString(BitSet bitSet) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            stringBuilder.append(bitSet.get(i) ? '1' : '0');
        }
        return stringBuilder.toString();
    }
}
