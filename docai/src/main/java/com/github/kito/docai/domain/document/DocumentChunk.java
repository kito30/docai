package com.github.kito.docai.domain.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pgvector.PGvector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    @JsonIgnore
    private Document document;

    @Column(columnDefinition = "TEXT")
    private String chunkText;

    @Column(columnDefinition = "vector(1536)") //text-embedding-3-small size
    private PGvector embedding;

    public DocumentChunk() {}

    public DocumentChunk(Document document, String chunkText) {
        this.document = document;
        this.chunkText = chunkText;
    }
    
    public Long getId() { return id; }
    public String getChunkText() { return chunkText; }
    public Document getDocument() { return document; }
    
    public PGvector getEmbedding() {return embedding; }
    public Long getDocumentId() {
        return document != null ? document.getId() : null;
    }
    public void setEmbedding(PGvector embedding) {this.embedding = embedding; }
}
