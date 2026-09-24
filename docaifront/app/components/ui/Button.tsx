"use client";

import { LucideIcon } from "lucide-react";

interface MyButtonProps {
    Icon: LucideIcon;
    onClick?: () => void;
    variant?: "default" | "ghost";  
    size?: number;
}

export default function MyButton({ Icon, variant = "default", onClick, size = 20 }: MyButtonProps) {
    const className = variant === "ghost"
        ? "bg-transparent border-none p-1.5 cursor-pointer flex justify-center items-center transition-all text-primary hover:text-primary-dark hover:scale-110 rounded-full"
        : "bg-primary text-white border-none w-11 h-11 rounded-full cursor-pointer flex justify-center items-center transition-all shadow-[0_4px_12px_rgba(192,75,122,0.3)] hover:bg-primary-dark hover:scale-105 shrink-0";

    return (
        <button type="button" onClick={onClick} className={className}>
            <Icon size={size} />
        </button>
    );
}