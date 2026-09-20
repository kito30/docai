import { ZoomOut, ZoomIn, Download } from "lucide-react";
import TopBar from "./ui/TopBar";
import IconButton from "./ui/IconButton";

export default function DocumentViewer() {
  return (
    <div className="flex flex-col gap-4 w-[450px] shrink-0">
      <TopBar title="No Document Selected">
        <IconButton icon={ZoomOut} variant="ghost" size={18} />
        <IconButton icon={ZoomIn} variant="ghost" size={18} />
        <IconButton icon={Download} variant="ghost" size={18} />
      </TopBar>

      <div className="bg-primary-light/15 rounded-[28px] shadow-cute border-[3px] border-white flex flex-col overflow-hidden flex-grow">
        <div className="p-8 overflow-y-auto flex-grow flex justify-center">
          {/* The extracted PDF text from the API will be rendered here */}
        </div>
      </div>
    </div>
  );
}
