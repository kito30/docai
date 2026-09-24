package com.github.kito.docai.domain.document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Column(columnDefinition = "vector(1536)") //text-embedding-3-small size
    private float[] embedding;

    public DocumentChunk() {}

    public DocumentChunk(Document document, String chunkText) {
        this.document = document;
        this.chunkText = chunkText;
    }
    
    public Long getId() { return id; }
    public String getChunkText() { return chunkText; }
    public Document getDocument() { return document; }
    
    public float[] getEmbedding() {return embedding; }
    public Long getDocumentId() {
        return document != null ? document.getId() : null;
    }
    public void setEmbedding(float[] embedding) {this.embedding = embedding; }
}
