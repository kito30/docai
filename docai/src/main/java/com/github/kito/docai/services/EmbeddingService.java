package com.github.kito.docai.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.ContentEmbedding;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;

@Service
public class EmbeddingService {

    private final GeminiClientService geminiClientService;

    public EmbeddingService(GeminiClientService geminiClientService) {
        this.geminiClientService = geminiClientService;
    }
    
    public float[] embedVector(String text) { // return array of float values for PGvector to stored in the database
        Client client = geminiClientService.getClient();

        EmbedContentResponse response = client.models.embedContent(
            "gemini-embedding-001",
            text,
            EmbedContentConfig.builder().outputDimensionality(1536).build()
        );
        
        if (response.embeddings().isEmpty() || response.embeddings().get().isEmpty()) {
            throw new RuntimeException("No embeddings returned by Gemini API");
        }
        
        ContentEmbedding embedding = response.embeddings().get().get(0);
        List<Float> values = embedding.values().orElseThrow(() -> new RuntimeException("Embedding vector values are empty"));
        
        float [] vector = new float[values.size()];
        for (int i = 0; i < values.size(); i++) {
            vector[i] = values.get(i);
        }
        return vector; 
    }
}
