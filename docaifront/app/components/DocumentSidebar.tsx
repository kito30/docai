import { FolderArchive, PlusCircle } from "lucide-react";
import TopBar from "./ui/TopBar";

export default function DocumentSidebar() {
  return (
    <div className="flex flex-col gap-4 w-[300px] shrink-0">
      <TopBar 
        icon={FolderArchive} 
        title="My Files" 
        actionIcon={PlusCircle} 
      />

      <div className="bg-card rounded-[28px] shadow-cute border-[3px] border-white flex flex-col overflow-hidden flex-grow">
        <div className="p-4 px-4 overflow-y-auto flex-grow flex flex-col gap-2">
          {/* Fetched documents from the API will be mapped here */}
        </div>
      </div>
    </div>
  );
}
