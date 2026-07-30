package com.github.kito.docai.domain.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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


    public DocumentChunk() {}

    public DocumentChunk(Document document, String chunkText) {
        this.document = document;
        this.chunkText = chunkText;
    }

    public Long getId() { return id; }
    public String getChunkText() { return chunkText; }
    public Document getDocument() { return document; }

    public Long getDocumentId() {
        return document != null ? document.getId() : null;
    }
}
