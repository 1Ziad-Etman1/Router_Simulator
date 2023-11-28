package com.example.standard_huffman;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class for Huffman decompression
public class DeCompressor {

    public String decompress(String compressed) {
        String[] parts = compressed.split("\n");
        ArrayList<String> partss = new ArrayList<>();
        for (String part : parts){
            partss.addLast(part);
        }
        for (String part : partss){
            System.out.println(part);;
        }

        if (partss.size() < 2) {
            // Handle the case where there is no data part in the compressed string
            throw new IllegalArgumentException("Invalid compressed string format");
        }

        Map<String, Character> decodingMap = new HashMap<>();
        String data = "";

        // Skip the first line, as it contains the codebook
        for (int i = 0; i < partss.size()-1; i++) {
            String line = partss.get(i);
            if(line.length() > 8){
                decodingMap.put(line.substring(8), binaryStringToChar(line.substring(0, 8)));
            }
        }

        data = partss.get(partss.size() - 1);

        StringBuilder decompressed = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : data.toCharArray()) {
            currentCode.append(bit);

            if (decodingMap.containsKey(currentCode.toString())) {
                decompressed.append(decodingMap.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }

        return decompressed.toString();
    }

    private char binaryStringToChar(String binaryString) {
        // Ensure that the binary string has exactly 8 bits
        if (binaryString.length() != 8 || !binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("Binary string must be of length 8 and contain only 0s and 1s");
        }

        // Parse the binary string as an integer
        int intValue = Integer.parseInt(binaryString, 2);

        // Cast the integer value to char
        return (char) intValue;
    }

    private Map<String, Character> buildDecodingMap(String codebook) {
        Map<String, Character> decodingMap = new HashMap<>();

        String[] pairs = codebook.split("(?<=\\G.{2})", -1);

        for (int i = 0; i < pairs.length; i += 2) {
            char character = pairs[i].charAt(0);
            String code = pairs[i + 1];
            decodingMap.put(code, character);
        }

        return decodingMap;
    }
}
