import { useEffect, useState } from 'react';
import { deleteDocument, listDocuments } from '../../api/documentApi';

export default function DocumentLibrary() {
  const [documents, setDocuments] = useState([]);

  const refresh = async () => {
    const result = await listDocuments();
    setDocuments(result);
  };

  useEffect(() => {
    refresh();
  }, []);

  const handleDelete = async (id) => {
    await deleteDocument(id);
    await refresh();
  };

  return (
    <div>
      <h3>Document library</h3>
      <ul>
        {documents.map((document) => (
          <li key={document.id} style={{ marginBottom: 8 }}>
            <strong>{document.name}</strong> · {document.pageCount} pages · {document.chunkCount} chunks
            <button type="button" onClick={() => handleDelete(document.id)} style={{ marginLeft: 8 }}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
