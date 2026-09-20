"use client";

import DocumentSidebar from "./components/DocumentSidebar";
import ChatInterface from "./components/ChatInterface";
import DocumentViewer from "./components/DocumentViewer";

export default function Home() {
  return (
    <div className="flex gap-6 p-8 h-screen w-full bg-surface">
      <DocumentSidebar />
      <ChatInterface />
      <DocumentViewer />
    </div>
  );
}
