package com.github.kito.docai.domain.document;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.kito.docai.services.DocumentService;

/**
 * REST controller for managing documents.
 * Provides endpoints to upload new documents (PDFs), retrieve metadata about existing documents,
 * and view the individual text chunks that a document was split into.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile inputFile) throws IOException {
        Document savedDocument = documentService.ingestDocument(inputFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @GetMapping("/chunks")
    public List<DocumentChunk> getAllChunks() {
        return documentService.getAllChunks();
    }

    @GetMapping("/{id}/chunks")
    public List<DocumentChunk> getChunksByDocumentId(@PathVariable Long id) {
        return documentService.getChunksByDocumentId(id);
    }
}
