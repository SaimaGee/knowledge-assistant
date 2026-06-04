import { useState } from "react";
import { uploadDocument } from "../services/api";

export default function UploadDocument() {
    const [file, setFile] = useState(null);
    const [status, setStatus] = useState("");
    const [uploading, setUploading] = useState(false);

    const upload = async () => {
        if (!file) {
            setStatus("Please select a PDF file first.");
            return;
        }

        setUploading(true);
        setStatus("Uploading...");

        try {
            const result = await uploadDocument(file);
            setStatus(`Uploaded successfully. Extracted text length: ${result.length}`);
        } catch (error) {
            setStatus(`Upload failed: ${error.message}`);
        } finally {
            setUploading(false);
        }
    };

    return (
        <div>
            <input
                type="file"
                accept=".pdf"
                onChange={(e) => setFile(e.target.files?.[0] ?? null)}
            />

            <button type="button" onClick={upload} disabled={uploading}>
                {uploading ? "Uploading..." : "Upload"}
            </button>

            {file && <div>Selected file: {file.name}</div>}
            {status && <div>{status}</div>}
        </div>
    );
}