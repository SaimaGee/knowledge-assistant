import { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { deleteConversation, getConversation, listConversations, askQuestion as askQuestionApi } from '../api/chatApi';

const ChatContext = createContext(null);

export function ChatProvider({ children }) {
  const [conversations, setConversations] = useState([]);
  const [activeConversationId, setActiveConversationId] = useState(null);
  const [messages, setMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    refreshConversations();
  }, []);

  const refreshConversations = async () => {
    const result = await listConversations();
    setConversations(result);
  };

  const loadConversation = async (id) => {
    const conversation = await getConversation(id);
    setActiveConversationId(id);
    setMessages(conversation.messages || []);
  };

  const askQuestion = async (message) => {
    setLoading(true);
    const response = await askQuestionApi(message, activeConversationId);
    const nextId = response.conversationId || activeConversationId;
    if (nextId) {
      setActiveConversationId(nextId);
    }
    await refreshConversations();
    if (nextId) {
      await loadConversation(nextId);
    }
    setLoading(false);
    return response;
  };

  const deleteCurrentConversation = async () => {
    if (!activeConversationId) return;
    await deleteConversation(activeConversationId);
    setActiveConversationId(null);
    setMessages([]);
    await refreshConversations();
  };

  const value = useMemo(() => ({
    conversations,
    activeConversationId,
    messages,
    loading,
    setMessages,
    refreshConversations,
    loadConversation,
    askQuestion,
    deleteCurrentConversation,
  }), [conversations, activeConversationId, messages, loading]);

  return <ChatContext.Provider value={value}>{children}</ChatContext.Provider>;
}

export function useChat() {
  return useContext(ChatContext);
}
