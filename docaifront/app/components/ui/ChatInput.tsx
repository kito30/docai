"use client";

import { useState } from "react";
import { Smile, Send, Plus } from "lucide-react";
import IconButton from "./IconButton";
import UploadIconButton from "./UploadIconButton";
import { Document } from "@/lib/types";

interface ChatInputProps {
  placeholder?: string;
  onSend?: (text: string) => void;
  onUploadSuccess?: (doc: Document) => void;
}

export default function ChatInput({ placeholder = "Type a message...", onSend, onUploadSuccess }: ChatInputProps) {
  const [text, setText] = useState("");

  const handleSend = () => {
    if (text.trim() && onSend) {
      onSend(text.trim());
      setText("");
    }
  };

  return (
    <div className="flex gap-3 items-center bg-surface p-2.5 pl-5 rounded-full border-2 border-transparent transition-all focus-within:border-primary-light focus-within:bg-white focus-within:shadow-[0_4px_12px_rgba(241,183,201,0.2)]">
      {/* The + sign is now the document upload button */}
      <UploadIconButton 
        icon={Plus} 
        size={18} 
        variant="ghost"
        onUploadSuccess={onUploadSuccess}
      />
      <input 
        type="text" 
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder={placeholder}
        className="flex-grow border-none bg-transparent outline-none text-[16px] font-semibold text-primary-dark placeholder:text-text-main placeholder:opacity-60"
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            handleSend();
          }
        }}
      />
      <Smile className="w-6 h-6 text-text-main opacity-60 cursor-pointer hover:opacity-100 hover:text-primary transition-colors shrink-0" />
      <IconButton icon={Send} size={18} onClick={handleSend} />
    </div>
  );
}