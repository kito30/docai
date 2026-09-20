"use client";

import { FolderArchive, PlusCircle } from "lucide-react";

export default function DocumentSidebar() {
  return (
    <div className="flex flex-col gap-4 w-[300px] shrink-0">
      
      <div className="bg-primary-light p-4 px-6 rounded-[24px] shadow-cute border-[3px] border-white flex items-center justify-between font-extrabold text-[16px] text-primary-dark shrink-0">
        <div className="flex items-center gap-2.5">
          <FolderArchive className="w-6 h-6 text-primary-dark" />
          <span>My Files</span>
        </div>
        <button 
          type="button"
          className="text-primary-dark/70 cursor-pointer hover:text-primary-dark hover:scale-110 transition-all p-1"
        >
          <PlusCircle className="w-5 h-5 text-primary-dark" />
        </button>
      </div>

      <div className="bg-primary-light rounded-[28px] shadow-cute border-[3px] border-white flex flex-col overflow-hidden flex-grow text-primary-dark">
        <div className="p-4 px-4 overflow-y-auto flex-grow flex flex-col gap-2">
          {/* Fetched documents from the API will be mapped here */}
          <div className="text-xs text-primary-dark/60 italic text-center py-6">
            No files uploaded yet
          </div>
        </div>
      </div>
      
    </div>
  );
}
