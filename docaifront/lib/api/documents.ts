import { Document, DocumentChunk } from '../types';
import { API_BASE_URL } from './config';

/**
 * 1. POST /api/documents
 * Uploads a PDF document to be extracted, chunked, and embedded.
 */
export async function uploadDocument(file: File): Promise<Document> {
  if (!file) {
    throw new Error("No file was provided.");
  }

  if (file.type !== "application/pdf" && !file.name.toLowerCase().endsWith(".pdf")) {
    throw new Error("Invalid file type. Please upload a PDF document.");
  }

  const MAX_FILE_SIZE = 10 * 1024 * 1024;
  if (file.size > MAX_FILE_SIZE) {
    throw new Error("File is too large. Please upload a file smaller than 10MB.");
  }

  const formData = new FormData();
  formData.append("file", file);

  const response = await fetch(`${API_BASE_URL}/documents`, {
    method: "POST",
    body: formData,
  });

  if (!response.ok) {
    throw new Error(`Failed to upload document: ${response.statusText}`);
  }
  return response.json();
}

/**
 * 3. GET /api/documents
 * Retrieves a list of all uploaded documents.
 */
export async function getAllDocuments(): Promise<Document[]> {
  const response = await fetch(`${API_BASE_URL}/documents`, {
    method: "GET",
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch documents: ${response.statusText}`);
  }
  return response.json();
}

/**
 * 4. GET /api/documents/{id}
 * Retrieves the details of a specific document.
 */
export async function getDocumentById(id: number): Promise<Document> {
  const response = await fetch(`${API_BASE_URL}/documents/${id}`, {
    method: "GET",
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch document: ${response.statusText}`);
  }
  return response.json();
}

/**
 * 5. GET /api/documents/chunks
 * Retrieves all chunks across all documents.
 */
export async function getAllChunks(): Promise<DocumentChunk[]> {
  const response = await fetch(`${API_BASE_URL}/documents/chunks`, {
    method: "GET",
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch all chunks: ${response.statusText}`);
  }
  return response.json();
}

/**
 * 6. GET /api/documents/{id}/chunks
 * Retrieves all chunks for a specific document.
 */
export async function getChunksByDocumentId(id: number): Promise<DocumentChunk[]> {
  const response = await fetch(`${API_BASE_URL}/documents/${id}/chunks`, {
    method: "GET",
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch document chunks: ${response.statusText}`);
  }
  return response.json();
}
