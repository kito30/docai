package com.github.kito.docai.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.kito.docai.domain.document.Document;
import com.github.kito.docai.domain.document.DocumentChunk;
import com.github.kito.docai.domain.document.DocumentChunkRepository;
import com.github.kito.docai.domain.document.DocumentRepository;
import com.pgvector.PGvector;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository documentChunkRepository;
    private final PdfExtractionService pdfExtractionService;
    private final PdfChunkingService pdfChunkingService;
    private final EmbeddingService embeddingService;

    private final Path uploadDir = Paths.get("uploads").toAbsolutePath();

    public DocumentService(
        DocumentRepository documentRepository,
        DocumentChunkRepository documentChunkRepository,
        PdfExtractionService pdfExtractionService,
        PdfChunkingService pdfChunkingService,
        EmbeddingService embeddingService
    ) {
        this.documentRepository = documentRepository;
        this.documentChunkRepository = documentChunkRepository;
        this.pdfExtractionService = pdfExtractionService;
        this.pdfChunkingService = pdfChunkingService;
        this.embeddingService = embeddingService;
    }

    @Transactional
    public Document ingestDocument(MultipartFile inputFile) throws IOException {
        if (inputFile == null || inputFile.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload an empty file");
        }

        Files.createDirectories(uploadDir);
        String filename = inputFile.getOriginalFilename() != null ? inputFile.getOriginalFilename() : "uploaded_document.pdf";
        Path filePath = uploadDir.resolve(filename);
        File destinationFile = filePath.toFile();
        inputFile.transferTo(destinationFile);

        String extractedText = pdfExtractionService.extractText(destinationFile);
        List<String> chunks = pdfChunkingService.chunkText(extractedText);

        Document document = new Document(filename);
        document.setStatus("READY");
        Document savedDocument = documentRepository.save(document);

        for (String chunkText : chunks) {
            float[] vector = embeddingService.embedVector(chunkText);
            DocumentChunk chunk = new DocumentChunk(savedDocument, chunkText);
            chunk.setEmbedding(new PGvector(vector));
            documentChunkRepository.save(chunk);
            savedDocument.addChunk(chunk);
        }

        return savedDocument;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found with ID: " + id));
    }

    public List<DocumentChunk> getAllChunks() {
        return documentChunkRepository.findAll();
    }

    public List<DocumentChunk> getChunksByDocumentId(Long documentId) {
        return documentChunkRepository.findByDocumentId(documentId);
    }
}
