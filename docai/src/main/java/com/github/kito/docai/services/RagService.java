package com.github.kito.docai.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.kito.docai.domain.document.DocumentChunk;
import com.github.kito.docai.domain.document.DocumentChunkRepository;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.pgvector.PGvector;

@Service
public class RagService {
    private final EmbeddingService embeddingService;
    private final GeminiClientService geminiClientService;
    private final DocumentChunkRepository documentChunkRepository;

    public RagService(
        EmbeddingService embeddingService,
        GeminiClientService geminiClientService,
        DocumentChunkRepository documentChunkRepository
    ) {
        this.embeddingService = embeddingService;
        this.geminiClientService = geminiClientService;
        this.documentChunkRepository = documentChunkRepository;
    }

    public String askQuestion(String question) {
        return askQuestion(question, null);
    }

    public String askQuestion(String question, Long documentId) {
        float[] embValue = embeddingService.embedVector(question);
        String vectorString = new PGvector(embValue).toString();

        List<DocumentChunk> similarChunks;
        if (documentId != null) {
            similarChunks = documentChunkRepository.findSimilarChunksByDocumentId(documentId, vectorString, 5);
        } else {
            similarChunks = documentChunkRepository.findSimilarChunks(vectorString, 5);
        }

        if (similarChunks.isEmpty()) {
            return "No relevant context found in the uploaded documents to answer your question.";
        }

        String context = similarChunks.stream()
                .filter(chunk -> chunk != null && chunk.getChunkText() != null)
                .map(chunk -> chunk.getChunkText())
                .collect(Collectors.joining("\n\n---\n\n"));

        // IDK man found this prompt online maybe will change it lol
        String prompt = """
            You are a helpful AI document assistant. Answer the user's question based strictly on the provided context below.
            If the answer cannot be found in the context, clearly state that the provided documents do not contain the answer.

            Context:
            %s

            Question:
            %s
            """.formatted(context, question);

        Client client = geminiClientService.getClient();
        GenerateContentResponse response = client.models.generateContent(
            "gemini-2.5-flash",
            prompt,
            null
        );

        return response.text();
    }
}
