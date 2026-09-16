# DocAI Backend Code Guid

## 1. API LINKS (Controllers)

### POST METHODS (Creating things)
- **/documents**: This is where you upload a PDF file. The code reads the text, cuts it into small paragraphs, turns them into numbers (vectors), and saves them.
    - *Files used:*
        - `DocumentController.java`: Gets the file from the user.
        - `DocumentService.java`: The manager that tells all the other files what to do.
        - `PdfExtractionService.java`: Reads the words out of the PDF.
        - `PdfChunkingService.java`: Cuts the words into small paragraphs (chunks).
        - `EmbeddingService.java`: Turns the text into math numbers (vectors) using Gemini.

- **/chat**: This is where you send a question. The code searches the database for paragraphs that match your question and asks the AI to answer it.
    - *Files used:*
        - `ChatController.java`: Gets the question from the user.
        - `RagService.java`: The manager for the Q&A feature.
        - `EmbeddingService.java`: Turns your question into numbers.
        - `DocumentChunkRepository.java`: Finds the matching paragraphs in the database.
        - `GeminiClientService.java`: Sends the paragraphs and your question to Gemini to get an answer.

### GET METHODS (Reading things)
- **/documents**: Gets a list of all the PDF files you have uploaded.
    - *Flow:* `DocumentController.java` -> `DocumentService.java` -> `DocumentRepository.java`.
- **/documents/{id}**: Gets the details of one specific PDF file.
- **/documents/chunks**: Gets every single text paragraph currently saved in the database.
- **/documents/{id}/chunks**: Gets only the text paragraphs that belong to one specific PDF file.

---

## 2. DATA STRUCTURES (Database Tables & Data Packages)

### Document.java (The `document` table in the database)
- *Variables:*
    - `id`: The unique ID number of the file.
    - `filename`: The name of the file (like *contract.pdf*).
    - `status`: What the file is doing right now (like *PROCESSING* or *READY*).
    - `uploadedAt`: The time you uploaded it.
    - `chunks`: A list of all the small text paragraphs that belong to this file.
- *Methods:* Basic commands to get or set these variables, plus a command to add a new paragraph to the list (`addChunk`).

### DocumentChunk.java (The `document_chunk` table in the database)
- *Variables:*
    - `id`: The unique ID number of the paragraph.
    - `document`: Links this paragraph back to the main file it came from.
    - `chunkText`: The actual words read from the PDF.
    - `embedding`: The numbers (vector) that represent what this text means.
- *Methods:* Basic commands to get or set these variables. It also has `getDocumentId()` which easily grabs the parent file's ID number.

### ChatRequest.java (Data sent from the user)
- *Variables:*
    - `question`: The question the user is asking.
    - `documentId`: (Optional) The ID of a specific file. If left empty, the AI will search through all files.

### ChatResponse.java (Data sent back to the user)
- *Variables:*
    - `answer`: The text answer that Gemini wrote.

---

## 3. DATABASE ACCESS (Repositories)

- **DocumentRepository.java**
    - Uses built-in commands to save, find all, or find a specific file in the database.

- **DocumentChunkRepository.java**
    - *Method 1:* `findByDocumentId` -> Finds all the paragraphs for one file.
    - *Method 2:* `findSimilarChunks` -> Uses a special math command (`<=>`) to search the WHOLE database for the paragraphs that best match the user's question.
    - *Method 3:* `findSimilarChunksByDocumentId` -> Does the same math search, but ONLY looks inside one specific file.

---

## 4. HELPER SERVICES (Core Logic)

- **PdfExtractionService.java**: Has a tool called `extractText` that pulls all the raw words out of a PDF file.
- **PdfChunkingService.java**: Has a tool called `chunkText` that cuts a huge wall of text into smaller blocks of 500 characters. It overlaps them slightly so sentences don't get cut off in the middle.
- **EmbeddingService.java**: Has a tool called `embedVector` that sends text to Google Gemini to get numbers back.
- **GeminiClientService.java**: Reads your secret API key from the `.env` file so the app can connect to Google.
