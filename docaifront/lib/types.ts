export interface Document {
  id: number;
  filename: string;
  status: string;
  uploadedAt: string;
  chunks: DocumentChunk[];
}

export interface DocumentChunk {
  id: number;
  chunkText: string;
  // omitting full document reference to avoid circular nesting in UI
}

export interface ChatRequest {
  question: string;
  documentId?: number;
}

export interface ChatResponse {
  answer: string;
}
