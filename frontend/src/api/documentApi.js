const API_BASE = import.meta.env.VITE_API_BASE_URL || '/api';

export async function listDocuments() {
  const response = await fetch(`${API_BASE}/documents`);
  if (!response.ok) throw new Error('Failed to load documents');
  return response.json();
}

export async function uploadDocument(file) {
  const formData = new FormData();
  formData.append('file', file);

  const response = await fetch(`${API_BASE}/documents/upload`, {
    method: 'POST',
    body: formData,
  });

  if (!response.ok) throw new Error('Upload failed');
  return response.text();
}

export async function deleteDocument(id) {
  const response = await fetch(`${API_BASE}/documents/${id}`, { method: 'DELETE' });
  if (!response.ok) throw new Error('Failed to delete document');
}
