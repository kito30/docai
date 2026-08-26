package com.github.kito.docai.domain.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {
    List<DocumentChunk> findByDocumentId(Long documentId);

    // Search across ALL documents
    @Query(value = """
        SELECT * FROM document_chunk 
        ORDER BY embedding <=> CAST(:vector AS vector) 
        LIMIT :topK
        """, nativeQuery = true)
    List<DocumentChunk> findSimilarChunks(@Param("vector") String vector, @Param("topK") int topK);

    // Search within a SPECIFIC, similar document
    @Query(value = """
        SELECT * FROM document_chunk 
        WHERE document_id = :documentId
        ORDER BY embedding <=> CAST(:vector AS vector) 
        LIMIT :topK
        """, nativeQuery = true)
    List<DocumentChunk> findSimilarChunksByDocumentId(
        @Param("documentId") Long documentId, 
        @Param("vector") String vector, 
        @Param("topK") int topK
    );
}
