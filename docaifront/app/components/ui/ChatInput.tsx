"use client";

import { Smile, Send } from "lucide-react";
import IconButton from "./IconButton";

interface ChatInputProps {
  placeholder?: string;
  onSend?: (text: string) => void;
}

export default function ChatInput({ placeholder = "Type a message...", onSend }: ChatInputProps) {
  return (
    <div className="flex gap-3 items-center bg-surface p-2.5 pl-5 rounded-full border-2 border-transparent transition-all focus-within:border-primary-light focus-within:bg-white focus-within:shadow-[0_4px_12px_rgba(241,183,201,0.2)]">
      <Smile className="w-6 h-6 text-text-main opacity-60 cursor-pointer hover:opacity-100 hover:text-primary transition-colors shrink-0" />
      <input 
        type="text" 
        placeholder={placeholder}
        className="flex-grow border-none bg-transparent outline-none text-[16px] font-semibold text-primary-dark placeholder:text-text-main placeholder:opacity-60"
        onKeyDown={(e) => {
          if (e.key === 'Enter' && onSend) {
            onSend(e.currentTarget.value);
            e.currentTarget.value = '';
          }
        }}
      />
      <IconButton icon={Send} size={18} />
    </div>
  );
}
