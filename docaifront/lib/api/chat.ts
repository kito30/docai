import { ChatRequest, ChatResponse } from '../types';
import { API_BASE_URL } from './config';

/**
 * 2. POST /api/chat
 * Sends a question to the backend to search for chunks and get an AI answer.
 */
export async function sendChatRequest(request: ChatRequest): Promise<ChatResponse> {
  const response = await fetch(`${API_BASE_URL}/chat`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(request),
  });

  if (!response.ok) {
    throw new Error(`Failed to get chat response: ${response.statusText}`);
  }
  return response.json();
}
