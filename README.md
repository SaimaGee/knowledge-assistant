# AI Knowledge Assistant

Enterprise-grade AI Knowledge Assistant that enables users to upload documents and ask natural language questions using Retrieval Augmented Generation (RAG).

Built using Java Spring Boot, React, Ollama/Llama, Qdrant Vector Database and Docker.

---

## Documentation

- [Architecture](docs/architecture.md)
- [Setup Guide](docs/setup.md)
- [API Reference](docs/api.md)
- [RAG Flow](docs/rag-flow.md)
- [Deployment](docs/deployment.md)
- [Roadmap](docs/roadmap.md)

## Features

### AI Chat

- Conversational interface
- Context-aware responses
- Multi-turn conversations

### Document Management

- Upload PDF documents
- Extract text automatically
- Chunk and index content

### RAG Pipeline

- Semantic search
- Vector embeddings
- Context retrieval
- Source citations

### LLM Provider Abstraction

Supports:

- Ollama (Local Llama)
- Amazon Bedrock
- Anthropic Claude
- OpenAI

### Enterprise Features

- REST APIs
- Docker deployment
- Authentication
- Audit logging
- CI/CD ready

---

## Architecture

```text
React UI
    |
Spring Boot API
    |
+------------------+
|                  |
Ollama      Amazon Bedrock
Llama       Claude Sonnet
    |
Embedding Service
    |
Qdrant
    |
Knowledge Base
```

---

## Technology Stack

### Frontend

- React
- TypeScript
- Material UI
- Vite

### Backend

- Java 21
- Spring Boot 3
- Maven
- LangChain4j

### AI

- Ollama
- Llama 3.2
- Vector Embeddings

### Search

- Qdrant Vector Database

### Infrastructure

- Docker
- Docker Compose
- AWS (optional)

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** - [Download JDK 21](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+** - [Download Maven](https://maven.apache.org/download.cgi)
- **Node.js 18+** - [Download Node.js](https://nodejs.org/)
- **Docker & Docker Compose** - [Download Docker Desktop](https://www.docker.com/products/docker-desktop)
- **Ollama** - [Download Ollama](https://ollama.ai)
- **Git** - [Download Git](https://git-scm.com/)

### Step 1: Clone the Repository

```bash
git clone https://github.com/SaimaGee/knowledge-assistant.git
cd knowledge-assistant
```

### Step 2: Setup Ollama (Local LLM)

Ollama provides a local LLM runtime. Install and configure it:

```bash
# Install Ollama from https://ollama.ai

# Pull the Llama 3.2 model
ollama pull llama3.2

# Start Ollama service (runs on port 11434 by default)
ollama serve
```

Keep this terminal running - Ollama will serve as your local LLM provider.

### Step 3: Setup Vector Database (Qdrant)

Start Qdrant using Docker:

```bash
# Start Qdrant container (runs on port 6333)
docker run -d \
  --name qdrant \
  -p 6333:6333 \
  -v qdrant_storage:/qdrant/storage \
  qdrant/qdrant:latest
```

Verify Qdrant is running:
```bash
curl http://localhost:6333/health
```

### Step 4: Configure Backend (Spring Boot)

Navigate to the backend directory and configure the application:

```bash
cd backend

# Copy the example configuration (if available)
# cp src/main/resources/application.properties.example src/main/resources/application.properties

# Edit configuration if needed
# nano src/main/resources/application.properties
```

**Key Configuration (src/main/resources/application.properties):**

```properties
# Server Configuration
server.port=8080

# Qdrant Configuration
qdrant.host=localhost
qdrant.port=6333

# LLM Provider (choose one)
# For Ollama (Local):
llm.provider=ollama
ollama.base-url=http://localhost:11434

# For other providers, configure accordingly:
# llm.provider=bedrock|openai|anthropic
# llm.api-key=your-api-key

# Document Processing
document.upload.dir=./uploads
document.chunk.size=1024
document.chunk.overlap=200
```

### Step 5: Build and Start Backend

```bash
# From the backend directory
# Install dependencies and build
mvn clean install

# Start Spring Boot application
mvn spring-boot:run
```

The backend API will be available at `http://localhost:8080`

Verify the backend is running:
```bash
curl http://localhost:8080/api/health
```

### Step 6: Setup and Start Frontend (React)

Open a new terminal and navigate to the frontend:

```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will be available at `http://localhost:5173`

---

## Running the Complete Application

### Quick Start with Docker Compose

For a one-command setup (if docker-compose.yml is available):

```bash
# From the project root directory
docker-compose up -d

# This will start:
# - Qdrant (port 6333)
# - Spring Boot Backend (port 8080)
# - React Frontend (port 5173)
```

### Manual Startup (Recommended for Development)

Open 4 terminal windows and run these commands simultaneously:

**Terminal 1 - Ollama:**
```bash
ollama serve
```

**Terminal 2 - Qdrant (if not using Docker):**
```bash
docker run -d -p 6333:6333 -v qdrant_storage:/qdrant/storage qdrant/qdrant:latest
```

**Terminal 3 - Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Terminal 4 - Frontend:**
```bash
cd frontend
npm run dev
```

---

## Application URLs

Once everything is running:

- **Frontend UI**: http://localhost:5173
- **Backend API**: http://localhost:8080
- **Qdrant Dashboard**: http://localhost:6333/dashboard
- **Ollama API**: http://localhost:11434

---

## Troubleshooting

### Backend won't start
- Check Java version: `java -version` (should be 21+)
- Ensure Qdrant is running: `curl http://localhost:6333/health`
- Check ports aren't in use: `lsof -i :8080`
- Review logs in `backend/logs/`

### Frontend won't load
- Ensure Node.js is installed: `node -v` (should be 18+)
- Clear node_modules and reinstall: `rm -rf node_modules && npm install`
- Check port 5173 isn't in use: `lsof -i :5173`

### Ollama not responding
- Verify Ollama is running on port 11434
- Check if model is downloaded: `ollama list`
- Re-pull model: `ollama pull llama3.2`

### Qdrant connection issues
- Verify Qdrant container: `docker ps | grep qdrant`
- Check Qdrant logs: `docker logs qdrant`
- Health check: `curl http://localhost:6333/health`

### Document upload fails
- Ensure upload directory exists: `mkdir -p uploads`
- Check file permissions on upload directory
- Verify disk space is available
- Check backend logs for detailed error messages

---

## Building for Production

### Backend

```bash
cd backend

# Build JAR file
mvn clean package

# Run JAR
java -jar target/knowledge-assistant-*.jar
```

### Frontend

```bash
cd frontend

# Build optimized production build
npm run build

# Preview production build
npm run preview
```

---

## Environment Variables

Create a `.env` file in the root directory for sensitive configuration:

```env
# Ollama
OLLAMA_BASE_URL=http://localhost:11434

# Qdrant
QDRANT_HOST=localhost
QDRANT_PORT=6333

# Backend
SPRING_PORT=8080

# Optional: For cloud LLM providers
OPENAI_API_KEY=your-key-here
BEDROCK_API_KEY=your-key-here
ANTHROPIC_API_KEY=your-key-here
```

---

## API Documentation

Once the backend is running, access the API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

---

## Development

### Backend Development

```bash
cd backend

# Run tests
mvn test

# Run with debug mode
mvn spring-boot:run -Dspring-boot.run.arguments="--debug"

# Format code
mvn formatter:format
```

### Frontend Development

```bash
cd frontend

# Run tests
npm run test

# Lint and fix
npm run lint -- --fix

# Build type checking
npm run type-check
```

---

## Docker Deployment

### Build Docker Images

```bash
# Backend
cd backend
docker build -t knowledge-assistant-backend:latest .

# Frontend
cd frontend
docker build -t knowledge-assistant-frontend:latest .

# Push to registry (optional)
docker tag knowledge-assistant-backend:latest yourregistry/knowledge-assistant-backend:latest
docker push yourregistry/knowledge-assistant-backend:latest
```

### Deploy with Docker Compose

```bash
docker-compose -f docker-compose.prod.yml up -d
```

---

## Contributing

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Support

For issues and questions:

- Create an [Issue](https://github.com/SaimaGee/knowledge-assistant/issues)
- Check existing [Discussions](https://github.com/SaimaGee/knowledge-assistant/discussions)
- Review [Documentation](./docs)

---

## Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- UI powered by [React](https://react.dev) and [Material UI](https://mui.com)
- LLM integration via [LangChain4j](https://github.com/langchain4j/langchain4j)
- Vector database: [Qdrant](https://qdrant.tech)
- Local LLM: [Ollama](https://ollama.ai)
