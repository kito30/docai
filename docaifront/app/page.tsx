"use client";

import { useState } from "react";
import DocumentSidebar from "./components/DocumentSidebar";
import ChatInterface from "./components/ChatInterface";
import DocumentViewer from "./components/DocumentViewer";
import { Document } from "@/lib/types";

export default function Home() {
  const [selectedDocument, setSelectedDocument] = useState<Document | null>(null);

  return (
    <div className="flex gap-6 p-8 h-screen w-full bg-surface overflow-hidden">
      {/* Left Sidebar: handles uploading & listing */}
      <DocumentSidebar 
        selectedDocumentId={selectedDocument?.id ?? null}
        onSelectDocument={(doc) => setSelectedDocument(doc)}
      />

      {/* Center Chat Interface */}
      <ChatInterface 
        onUploadSuccess={(doc) => setSelectedDocument(doc)}
      />

      {/* Right Document Viewer */}
      <DocumentViewer />
    </div>
  );
}
