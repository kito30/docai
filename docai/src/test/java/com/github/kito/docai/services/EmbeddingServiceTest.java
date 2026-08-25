package com.github.kito.docai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

// Only load GeminiClientService and EmbeddingService (skips Database/JPA connection)
@SpringBootTest(classes = {GeminiClientService.class, EmbeddingService.class})
@TestPropertySource(locations = "classpath:application.properties")
class EmbeddingServiceTest {

    @Autowired(required = false)
    private EmbeddingService embeddingService;

    // Approach 1: Standalone test (reads from .env automatically, no Spring Boot context required)
    @Test
    void testEmbedVector_Standalone() {
        GeminiClientService clientService = new GeminiClientService("");
        EmbeddingService service = new EmbeddingService(clientService);

        float[] vector = service.embedVector("Hello Gemini embedding test!");

        assertNotNull(vector, "Embedding vector should not be null");
        assertEquals(1536, vector.length, "Embedding vector dimensionality should be 1536");
        System.out.println("Standalone Test: Vector length = " + vector.length);
    }

    // Approach 2: Full Spring Boot Context integration test
    @Test
    void testEmbedVector_WithSpring() {
        assertNotNull(embeddingService, "EmbeddingService bean should be initialized in Spring context");

        String sampleText = "Hello Gemini embedding test!";
        float[] vector = embeddingService.embedVector(sampleText);

        assertNotNull(vector, "Embedding vector should not be null");
        System.out.println("Spring Test: Vector = " + java.util.Arrays.toString(vector));
        System.out.println("Vector length = " + vector.length);
        assertEquals(1536, vector.length, "Vector dimensionality for gemini-embedding-001 should be 1536");
        assertTrue(vector.length > 0);
        

        System.out.println("--- EMBEDDING TEST RESULT ---");
        System.out.println("Vector dimensionality (length): " + vector.length);
        System.out.println("First 5 values: ");
        for (int i = 0; i < Math.min(5, vector.length); i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println("\n-----------------------------");
    }
}
