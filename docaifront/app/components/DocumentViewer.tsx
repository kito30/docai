"use client";

import { ZoomOut, ZoomIn, Download } from "lucide-react";
import TopBar from "./ui/TopBar";
import Button from "./ui/Button";

export default function DocumentViewer() {
  return (
    <div className="flex flex-col gap-4 w-[450px] shrink-0">
      <TopBar title="No Document Selected">
        <Button Icon={ZoomOut} variant="ghost" size={18} />
        <Button Icon={ZoomIn} variant="ghost" size={18} />
        <Button Icon={Download} variant="ghost" size={18} />
      </TopBar>

      <div className="bg-primary-light/15 rounded-[28px] shadow-cute border-[3px] border-white flex flex-col overflow-hidden flex-grow">
        <div className="p-8 overflow-y-auto flex-grow flex flex-col items-center justify-center text-center">
          <p className="text-sm font-semibold text-text-main opacity-60">
            No document selected yet.
          </p>
          <p className="text-xs text-text-main opacity-40 mt-1">
            Click the + icon in the chat to upload a PDF.
          </p>
        </div>
      </div>
    </div>
  );
}
