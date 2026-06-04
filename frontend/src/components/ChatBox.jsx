import { useState } from "react";
import { askQuestion } from "../services/api";

export default function ChatBox() {
    const [message, setMessage] = useState("");
    const [answer, setAnswer] = useState("");
    const [status, setStatus] = useState("");
    const [loading, setLoading] = useState(false);

    const ask = async () => {
        if (!message.trim()) {
            setStatus("Please enter a question.");
            return;
        }

        setLoading(true);
        setStatus("");

        try {
            const response = await askQuestion(message);
            setAnswer(response.answer ?? "No answer returned.");
        } catch (error) {
            setStatus(error.message || "Failed to ask question.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <input
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Enter your question"
            />

            <button type="button" onClick={ask} disabled={loading}>
                {loading ? "Asking..." : "Ask"}
            </button>

            {status && <div>{status}</div>}

            {answer && (
                <div>
                    <hr />
                    <div>{answer}</div>
                </div>
            )}
        </div>
    );
}
