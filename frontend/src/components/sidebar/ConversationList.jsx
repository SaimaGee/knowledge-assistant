import { useChat } from '../../context/ChatContext';

export default function ConversationList() {
  const { conversations, loadConversation, activeConversationId, deleteCurrentConversation } = useChat();

  return (
    <div>
      <h3>Conversations</h3>
      <ul>
        {conversations.map((conversation) => (
          <li key={conversation.id} style={{ marginBottom: 8 }}>
            <button type="button" onClick={() => loadConversation(conversation.id)}>
              {conversation.title || 'Conversation'}
            </button>
          </li>
        ))}
      </ul>
      {activeConversationId && (
        <button type="button" onClick={deleteCurrentConversation}>Delete current</button>
      )}
    </div>
  );
}
