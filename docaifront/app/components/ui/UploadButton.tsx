"use client";

import { uploadDocument } from "@/lib/api";
import { useRef } from "react";
import { Upload } from "lucide-react";

export default function UploadButton() {

    const inputRef = useRef<HTMLInputElement>(null);

    const handleChange = async (event : React.ChangeEvent<HTMLInputElement>) => {

    const file = event.target.files?.[0];
    
        if (file) {

            await uploadDocument(file);

        }
    };

    return (
        <div>

            <button 
                onClick={() => inputRef.current?.click()}
                type="button"
                className="bg-primary-light/15 rounded-[28px] shadow-cute border-[3px] border-white flex items-center justify-center gap-2 font-extrabold text-[16px] text-primary-dark shrink-0 px-6 h-[48px] hover:bg-primary-light/25 transition-all cursor-pointer"
            >
                <Upload className="w-5 h-5 text-primary" />
                <span>Upload Document</span>
            </button>

            <input type="file" 
            id = "upload"
            className="hidden"
            ref={inputRef}
            onChange= {handleChange}
            />  
        </div>
   );

}
