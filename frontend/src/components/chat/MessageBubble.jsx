import CitationCard from './CitationCard';

export default function MessageBubble({ message }) {
  return (
    <div className="message assistant">
      <p>{message.content}</p>
      {message.citations?.length > 0 && (
        <div className="citations">
          {message.citations.map(c => (
            <CitationCard key={c.documentId + c.pageNumber} citation={c} />
          ))}
        </div>
      )}
    </div>
  );
}