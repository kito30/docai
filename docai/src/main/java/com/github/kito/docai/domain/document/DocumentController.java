package com.github.kito.docai.domain.document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.kito.docai.services.PdfChunkingService;
import com.github.kito.docai.services.PdfExtractionService;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentChunkRepository documentChunkRepository;
    @Autowired
    private PdfExtractionService pdfExtractionService;
    @Autowired
    private PdfChunkingService pdfChunkingService;
    
    private final String uploadDir = "/uploads"; // Directory to store uploaded files

   
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
