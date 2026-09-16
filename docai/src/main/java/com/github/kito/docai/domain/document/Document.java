package com.github.kito.docai.domain.document;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entity representing an uploaded document in the database.
 * Holds metadata like filename, upload status, and a one-to-many relationship
 * with the individual text chunks that this document was split into.
 */
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String status;
    private LocalDateTime uploadedAt;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentChunk> chunks = new ArrayList<>();

    //constructor that do nothing I guess (will implement later)
    public Document() {}
    
    public Document(String filename){
        this.filename = filename;
        this.status = "PROCESSING";
        this.uploadedAt = LocalDateTime.now(); 
    }

    // getters and setters
    public Long getId() { return id; }
    public String getFilename() { return filename; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }

    public List<DocumentChunk> getChunks() { return chunks; }
    public void setChunks(List<DocumentChunk> chunks) { this.chunks = chunks; }

    public void addChunk(DocumentChunk chunk) {
        chunks.add(chunk);
    }
}
