package com.github.kito.docai.domain.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.kito.docai.services.EmbeddingService;
import com.github.kito.docai.services.PdfChunkingService;
import com.github.kito.docai.services.PdfExtractionService;
import com.pgvector.PGvector;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository documentChunkRepository;
    private final PdfExtractionService pdfExtractionService;
    private final PdfChunkingService pdfChunkingService;
    private final EmbeddingService embeddingService;
    
    private final String uploadDir = "uploads"; // Directory to store uploaded files

    public DocumentController(
            DocumentRepository documentRepository,
            DocumentChunkRepository documentChunkRepository,
            PdfExtractionService pdfExtractionService,
            PdfChunkingService pdfChunkingService,
            EmbeddingService embeddingService) {
        this.documentRepository = documentRepository;
        this.documentChunkRepository = documentChunkRepository;
        this.pdfExtractionService = pdfExtractionService;
        this.pdfChunkingService = pdfChunkingService;
        this.embeddingService = embeddingService;
    }
   
    @PostMapping
    public Document uploadDocument(@RequestParam("file") MultipartFile inputFile) throws IOException {
        // Logic to handle file upload and save document metadata to the database

        // get the absolute path to save
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();

        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(inputFile.getOriginalFilename());

        inputFile.transferTo(filePath.toFile());

        String extractedText = pdfExtractionService.extractText(filePath.toFile());

        List<String> chunks = pdfChunkingService.chunkText(extractedText);

        Document document = new Document(inputFile.getOriginalFilename());
        document.setStatus("Ready");

        Document savedDocument = documentRepository.save(document);

        for (String chunkText : chunks) {
            DocumentChunk chunk = new DocumentChunk(savedDocument, chunkText);
            float[] vector = embeddingService.embedVector(chunkText);
            chunk.setEmbedding(new PGvector(vector));
            documentChunkRepository.save(chunk);
        }

        return savedDocument;
    }
    @GetMapping()
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    @GetMapping("/chunks")
    public List<DocumentChunk> getAllChunks() {
        return documentChunkRepository.findAll();
    }

    @GetMapping("/{id}/chunks")
    public List<DocumentChunk> getChunksByDocumentId(@PathVariable Long id) {
        return documentChunkRepository.findByDocumentId(id);
    }
}
