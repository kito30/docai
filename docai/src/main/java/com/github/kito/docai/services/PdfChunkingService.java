package com.github.kito.docai.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PdfChunkingService {
    private static final int CHUNK_SIZE = 500; // Number of characters per chunk
    private static final int OVERLAP_SIZE = 50; // Number of overlapping characters between chunks for contexting the setences   
                                                // Chunk 1: [-----------------------450-500]
                                                // Chunk 2: [450-500-----------------------950]
    
                                                
                                        
    public List<String> chunkText(String text) {
        List<String> chunks = new ArrayList<>();
        String[] words = text.split("\\s+"); // Split the text into words

        int start = 0;
        while (start < words.length) {
            int end = Math.min(start + CHUNK_SIZE, words.length);
            String chunk = String.join("", Arrays.asList(words).subList(start, end));
            chunks.add(chunk);

            if (end == words.length) {
                break; // Reached the end of the text
            }
            start += (CHUNK_SIZE - OVERLAP_SIZE); // Move the start index for the next chunk with overlap
        }

        return chunks;
    }
}
