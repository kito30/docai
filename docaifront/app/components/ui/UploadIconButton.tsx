"use client";

import { useRef, useState } from "react";
import { LucideIcon, Loader2 } from "lucide-react";
import IconButton from "./IconButton";
import { uploadDocument } from "@/lib/api";
import { Document } from "@/lib/types";

interface UploadIconButtonProps {
  icon: LucideIcon;
  variant?: "primary" | "ghost" | "default";
  size?: number;
  onUploadSuccess?: (doc: Document) => void;
  onUploadError?: (error: string) => void;
}

export default function UploadIconButton({
  icon,
  variant = "ghost",
  size = 20,
  onUploadSuccess,
  onUploadError,
}: UploadIconButtonProps) {
  const [isUploading, setIsUploading] = useState(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    try {
      setIsUploading(true);
      const newDoc = await uploadDocument(file);
      onUploadSuccess?.(newDoc);
    } catch (err) {
      if (onUploadError) {
        const message = err instanceof Error ? err.message : "Upload failed";
        onUploadError(message);
      }
    } finally {
      setIsUploading(false);
      e.target.value = "";
    }
  };

  return (
    <>
      <IconButton
        icon={isUploading ? Loader2 : icon}
        variant={variant}
        size={size}
        disabled={isUploading}
        onClick={() => fileInputRef.current?.click()}
        className={isUploading ? "animate-spin opacity-60" : ""}
      />
      <input
        ref={fileInputRef}
        type="file"
        accept=".pdf"
        className="hidden"
        onChange={handleFileChange}
      />
    </>
  );
}
