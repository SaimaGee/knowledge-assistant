import { useEffect, useState } from 'react';
import { useChat } from '../../context/ChatContext';
import MessageBubble from './MessageBubble';

export default function ChatWindow() {
  const { messages, setMessages, askQuestion, loading } = useChat();
  const [draft, setDraft] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!draft.trim()) return;
    const optimistic = { role: 'USER', content: draft, citations: [] };
    setMessages((prev) => [...prev, optimistic]);
    setDraft('');
    const response = await askQuestion(draft);
    setMessages((prev) => [...prev, { role: 'ASSISTANT', content: response.answer || 'No answer returned.', citations: response.sources || [] }]);
  };

  return (
    <div>
      <div style={{ minHeight: 240, marginBottom: 12 }}>
        {messages.map((message, index) => (
          <MessageBubble key={`${message.role}-${index}`} message={message} />
        ))}
      </div>
      <form onSubmit={handleSubmit}>
        <input value={draft} onChange={(e) => setDraft(e.target.value)} placeholder="Ask a question" />
        <button type="submit" disabled={loading}>{loading ? 'Thinking...' : 'Send'}</button>
      </form>
    </div>
  );
}
