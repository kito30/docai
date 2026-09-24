"use client";

import { useState, useEffect } from "react";
import { FolderArchive, PlusCircle, FileText, Loader2 } from "lucide-react";
import UploadIconButton from "./ui/UploadIconButton";
import { getAllDocuments } from "@/lib/api";
import { Document } from "@/lib/types";

interface DocumentSidebarProps {
  selectedDocumentId?: number | null;
  onSelectDocument?: (doc: Document) => void;
}

export default function DocumentSidebar({
  selectedDocumentId,
  onSelectDocument,
}: DocumentSidebarProps = {}) {
  const [documents, setDocuments] = useState<Document[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  // Load existing documents when the sidebar first opens
  useEffect(() => {
    async function fetchDocs() {
      try {
        const docs = await getAllDocuments();
        setDocuments(docs || []);
      } catch (err) {
        console.error("Failed to fetch documents", err);
      } finally {
        setIsLoading(false);
      }
    }
    fetchDocs();
  }, []);

  return (
    <div className="flex flex-col gap-4 w-[300px] shrink-0">
      
      {/* TopBar Card in Soft Pink */}
      <div className="bg-primary-light p-4 px-6 rounded-[24px] shadow-cute border-[3px] border-white flex items-center justify-between font-extrabold text-[16px] text-primary-dark shrink-0">
        <div className="flex items-center gap-2.5">
          <FolderArchive className="w-6 h-6 text-primary-dark" />
          <span>My Files</span>
        </div>
        
        {/* Upload Button */}
        <UploadIconButton 
          icon={PlusCircle} 
          onUploadSuccess={(newDoc) => {
            // Instantly add to list and select it
            setDocuments((prev) => [newDoc, ...prev]);
            onSelectDocument?.(newDoc);
          }}
          onUploadError={(err) => alert("Upload failed: " + err)}
        />
      </div>

      {/* Main List Card in Soft Pink */}
      <div className="bg-primary-light rounded-[28px] shadow-cute border-[3px] border-white flex flex-col overflow-hidden flex-grow text-primary-dark">
        <div className="p-3 px-4 overflow-y-auto flex-grow flex flex-col gap-2">
          
          {isLoading ? (
            <div className="text-xs text-primary-dark/60 italic text-center py-6 flex justify-center items-center gap-2">
              <Loader2 className="w-4 h-4 animate-spin" /> Loading...
            </div>
          ) : documents.length === 0 ? (
            <div className="text-xs text-primary-dark/60 italic text-center py-6">
              No files uploaded yet
            </div>
          ) : (
            documents.map((doc) => {
              const isSelected = selectedDocumentId === doc.id;
              return (
                <div 
                  key={doc.id} 
                  onClick={() => onSelectDocument?.(doc)}
                  className={`p-3 px-4 flex items-center gap-3.5 cursor-pointer rounded-[22px] transition-all border-2 ${
                    isSelected
                      ? "bg-white shadow-sm border-primary text-primary-dark font-bold scale-[1.02]"
                      : "border-transparent bg-white/40 hover:bg-white/70 text-primary-dark font-semibold"
                  }`}
                >
                  <FileText className={`w-5 h-5 shrink-0 ${isSelected ? "text-primary" : "text-primary-dark/70"}`} />
                  <div className="flex flex-col min-w-0 flex-1">
                    <span className="text-[14px] leading-tight truncate">{doc.filename}</span>
                    <span className="text-[11px] opacity-70 mt-0.5">{doc.status || "READY"}</span>
                  </div>
                </div>
              );
            })
          )}

        </div>
      </div>
      
    </div>
  );
}
