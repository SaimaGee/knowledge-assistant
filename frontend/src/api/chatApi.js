const API_BASE = import.meta.env.VITE_API_BASE_URL || '/api';

export async function askQuestion(message, conversationId) {
  const response = await fetch(`${API_BASE}/chat`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ message, conversationId }),
  });

  if (!response.ok) {
    throw new Error('Failed to ask question');
  }

  return response.json();
}

export async function listConversations() {
  const response = await fetch(`${API_BASE}/conversations`);
  if (!response.ok) throw new Error('Failed to load conversations');
  return response.json();
}

export async function getConversation(id) {
  const response = await fetch(`${API_BASE}/conversations/${id}`);
  if (!response.ok) throw new Error('Failed to load conversation');
  return response.json();
}

export async function deleteConversation(id) {
  const response = await fetch(`${API_BASE}/conversations/${id}`, { method: 'DELETE' });
  if (!response.ok) throw new Error('Failed to delete conversation');
}
