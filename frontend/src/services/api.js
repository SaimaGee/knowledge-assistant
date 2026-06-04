const API_URL = "http://localhost:8080";

export async function uploadDocument(file) {
    if (!file) {
        throw new Error("No file selected for upload");
    }

    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch(
        `${API_URL}/api/documents/upload`,
        {
            method: "POST",
            body: formData
        }
    );

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || response.statusText);
    }

    return response.text();
}

export async function askQuestion(message) {

    const response = await fetch(
        `${API_URL}/api/chat`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                message
            })
        }
    );

    return response.json();
}