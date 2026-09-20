"use client";

import React from "react";
import { LucideIcon } from "lucide-react";

interface IconButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  icon: LucideIcon;
  variant?: 'primary' | 'ghost';
  size?: number;
}

export default function IconButton({ 
  icon: Icon, 
  variant = 'primary', 
  className = '', 
  size = 20,
  ...props 
}: IconButtonProps) {
  if (variant === 'ghost') {
    return (
      <button 
        type="button"
        className={`bg-transparent border-none p-1.5 cursor-pointer flex justify-center items-center transition-all text-primary hover:text-primary-dark hover:scale-110 rounded-full ${className}`}
        {...props}
      >
        <Icon size={size} />
      </button>
    );
  }

  return (
    <button 
      type="button"
      className={`bg-primary text-white border-none w-11 h-11 rounded-full cursor-pointer flex justify-center items-center transition-all shadow-[0_4px_12px_rgba(192,75,122,0.3)] hover:bg-primary-dark hover:scale-105 shrink-0 ${className}`}
      {...props}
    >
      <Icon size={size} />
    </button>
  );
}
