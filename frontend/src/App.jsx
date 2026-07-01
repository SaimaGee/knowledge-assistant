import { ChatProvider } from './context/ChatContext';
import ConversationList from './components/sidebar/ConversationList';
import ChatWindow from './components/chat/ChatWindow';
import UploadDropzone from './components/documents/UploadDropzone';
import DocumentLibrary from './components/documents/DocumentLibrary';

function App() {
    return (
        <ChatProvider>
            <div style={{ display: 'grid', gridTemplateColumns: '260px 1fr', gap: 16, padding: 16 }}>
                <aside style={{ borderRight: '1px solid #ddd', paddingRight: 12 }}>
                    <h2>Conversations</h2>
                    <ConversationList />
                </aside>
                <main>
                    <h1>AI Knowledge Assistant</h1>
                    <UploadDropzone />
                    <hr />
                    <ChatWindow />
                    <hr />
                    <DocumentLibrary />
                </main>
            </div>
        </ChatProvider>
    );
}

export default App;
