package com.github.kito.docai.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class GeminiClientService {

    private final Client client;

    public GeminiClientService(@Value("${gemini.api.key:}") String apiKey) {
        if (apiKey == null || apiKey.isBlank() || apiKey.startsWith("${")) {
            apiKey = loadApiKeyFromDotenv();
        }
        if (apiKey == null || apiKey.isBlank() || apiKey.startsWith("${")) {
            apiKey = System.getenv("GEMINI_API_KEY");
        }
        if (apiKey != null) {
            apiKey = apiKey.trim();
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                "Gemini API key is missing! Please provide GEMINI_API_KEY in your .env file, application.properties, or environment variables."
            );
        }
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    private static String loadApiKeyFromDotenv() {
        try {
            // Check current directory
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String key = dotenv.get("GEMINI_API_KEY");
            if (key != null && !key.isBlank()) {
                return key;
            }
            // Check parent directory
            Dotenv parentDotenv = Dotenv.configure().directory("..").ignoreIfMissing().load();
            return parentDotenv.get("GEMINI_API_KEY");
        } catch (Exception e) {
            return null;
        }
    }

    public Client getClient() {
        return this.client;
    }
}
