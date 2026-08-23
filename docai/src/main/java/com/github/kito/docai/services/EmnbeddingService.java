package com.github.kito.docai.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;

@Service
public class EmnbeddingService {
    @Value("${gemini.api.key}")
    private String apiKey;
    
    private final Client client = new Client();
    
    public float[] embedVector(String text) { // return array of float values for PGvector to stored in the database

        EmbedContentResponse response = client.models.embedContent(
            "gemini-embdedding-001",
            text,
            EmbedContentConfig.builder().outputDimensionality(1536).build()
        );
        
        List<Float> values = response.embeddings().get().get(0).values().orElse(null);
        
        float [] vector = new float[values.size()];
        for(int i = 0 ;i< values.size(); i++ ){
            vector[i] = values.get(i);
        }
        return vector; 
    }
}
