import "./globals.css";

;

export default function RootLayout({ children }: LayoutProps<"/">) {
  return (
    <html lang="en">
      <body className="min-h-screen bg-surface text-text-main flex flex-col font-sans">
        {children}
      </body>
    </html>
  );
}
