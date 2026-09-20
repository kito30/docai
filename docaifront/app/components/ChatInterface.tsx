import { Bot, MoreHorizontal } from "lucide-react";
import TopBar from "./ui/TopBar";
import ChatInput from "./ui/ChatInput";

export default function ChatInterface() {
  return (
    <div className="flex flex-col gap-4 flex-grow min-w-[350px]">
      <TopBar 
        icon={Bot} 
        title="DocAI Helper" 
        actionIcon={MoreHorizontal} 
      />
      
      <div className="bg-card rounded-[28px] shadow-cute border-[3px] border-white flex flex-col overflow-hidden flex-grow">
        <div className="flex-grow p-6 overflow-y-auto flex flex-col gap-6">
          {/* Chat messages from the API will be mapped here */}
        </div>
        
        <div className="p-5 px-6 border-t border-dashed border-border-soft">
          <ChatInput placeholder="Ask a question about your documents..." />
        </div>
      </div>
    </div>
  );
}
