package com.example.standard_huffman;

import java.util.HashMap;
import java.util.Map;

// Class for Huffman decompression
public class DeCompressor {

    public String decompress(String compressed) {
        String[] parts = compressed.split("\n", 2);

        String codebook = parts[0];
        String data = parts[1];

        Map<String, Character> decodingMap = buildDecodingMap(codebook);

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
