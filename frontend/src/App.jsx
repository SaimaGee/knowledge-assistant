import UploadDocument from "./components/UploadDocument";
import ChatBox from "./components/ChatBox";

function App() {
    return (
        <div>
            <h1>AI Knowledge Assistant</h1>
            <UploadDocument />
            <hr />
            <ChatBox />
        </div>
    );
}

export default App;
