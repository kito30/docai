"use client";

import React from "react";
import { LucideIcon } from "lucide-react";

interface TopBarProps {
  icon?: LucideIcon;
  title: string;
  actionIcon?: LucideIcon;
  onActionClick?: () => void;
  children?: React.ReactNode;
}

export default function TopBar({ 
  icon: Icon, 
  title, 
  actionIcon: ActionIcon, 
  onActionClick,
  children 
}: TopBarProps) {
  return (
    <div className="bg-card p-4 px-6 rounded-[24px] shadow-cute border-[3px] border-white flex items-center justify-between font-extrabold text-[16px] text-primary-dark shrink-0">
      <div className="flex items-center gap-2.5">
        {Icon && <Icon className="w-6 h-6 text-primary" />}
        <span>{title}</span>
      </div>
      
      {children ? (
        <div className="flex items-center gap-2">{children}</div>
      ) : (
        ActionIcon && (
          <button 
            onClick={onActionClick}
            className="text-text-main opacity-60 cursor-pointer hover:opacity-100 hover:text-primary transition-all p-1"
          >
            <ActionIcon className="w-5 h-5" />
          </button>
        )
      )}
    </div>
  );
}
