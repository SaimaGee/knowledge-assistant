# API Reference

## Upload PDF

POST

```http
/api/documents/upload
```

Content-Type:

```text
multipart/form-data
```

Example:

```bash
curl \
-F "file=@sample.pdf" \
http://localhost:8080/api/documents/upload
```

---

## Ask Question

POST

```http
/api/chat
```

Request:

```json
{
  "message": "What is AWS IAM?"
}
```

Response:

```json
{
  "answer": "AWS IAM manages access control..."
}
```
