# DocAI — Project Scope

## 1. Summary

A web application where users upload documents (PDFs, scanned images, contracts, etc.) — basically any docs in various forms, maybe for research papers? idk.

- Extracts text via scanning
- Lets users ask natural-language questions about their documents and get answers
- Supports semantic search across documents
- Tracks LLM API cost per user (maybe?)

## 2. Tech Stack

### Backend

- **Java 21** (LTS) with Spring Boot 3
- **Spring Data JPA** (Hibernate) for relational data (users, documents, metadata)
- **PostgreSQL** as the primary database
- **pgvector** extension on Postgres for vector similarity search on AI model
  — [Read more about vector search (IBM)](https://www.ibm.com/think/topics/vector-search)

### Frontend

- **Next.js**
- **Tailwind CSS**

### AI Stuff

- **LLM API** — TBD (will decide later)
- **Embedding API** for text embedding
- **OCR** — will be implemented in the near future
