Proj Spec or you could call it scope for DocAi
1. Summary:
- A web application where users upload documents (PDFs, scanned images, contracts,...) basically any docs in various form maybe for reasearch paper ? idk.
    * Extracts text via scanning
    * Lets user ask natural-language questions about their documents, get answers.
    * Supports semantic search across document
    * Tracks LLM API cost per user (maybe ?)
2. Tech Stack
- Backend
    * ASP.NET Core on .Net 10
    * Entity Framework Core for relation data (users, documents, metadata)
    * ProstgreSQL as primary database
    * pgvector extension on Postgres for vector similarity search on AI model.
