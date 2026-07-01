import { useState } from 'react';
import { uploadDocument } from '../../api/documentApi';

export default function UploadDropzone() {
  const [status, setStatus] = useState('');
  const [uploading, setUploading] = useState(false);

  const handleChange = async (event) => {
    const file = event.target.files?.[0];
    if (!file) return;

    setUploading(true);
    setStatus('Uploading...');
    try {
      await uploadDocument(file);
      setStatus(`Uploaded ${file.name}`);
    } catch (error) {
      setStatus(error.message || 'Upload failed');
    } finally {
      setUploading(false);
    }
  };

  return (
    <div>
      <input type="file" accept=".pdf" onChange={handleChange} />
      {uploading && <div>Uploading...</div>}
      {status && <div>{status}</div>}
    </div>
  );
}
