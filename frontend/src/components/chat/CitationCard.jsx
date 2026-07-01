export default function CitationCard({ citation }) {
  return (
    <div style={{ marginTop: 6, padding: 8, border: '1px solid #ddd', borderRadius: 6, background: '#fff' }}>
      <div><strong>{citation.documentName || 'Document'}</strong> · Page {citation.pageNumber || 1}</div>
      <div>{citation.snippet}</div>
    </div>
  );
}
