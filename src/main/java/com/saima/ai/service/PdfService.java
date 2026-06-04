package com.saima.ai.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final Path uploadPath;
    private final Path extractedPath;
    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    public PdfService(
            @Value("${pdf.upload-dir:uploads}") String uploadDir,
            @Value("${pdf.extracted-dir:uploads/extracted}") String extractedDir,
            EmbeddingService embeddingService,
            QdrantService qdrantService) {
        this.uploadPath = Paths.get(uploadDir);
        this.extractedPath = Paths.get(extractedDir);
        this.embeddingService = embeddingService;
        this.qdrantService = qdrantService;
    }

    public String extractText(MultipartFile file)
        throws Exception {

        saveFile(file);

        String extractedText;

        try (PDDocument document =
                    Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper =
                    new PDFTextStripper();

            extractedText =
                    stripper.getText(document);
        }

        saveExtractedText(
                file.getOriginalFilename(),
                extractedText);

        return extractedText;
    }

    public String extractTextAndIndex(MultipartFile file)
            throws Exception {
        String extractedText = extractText(file);
        indexText(file.getOriginalFilename(), extractedText);
        return extractedText;
    }

    private void indexText(String fileName, String text) {
        List<String> chunks = chunkText(text, 500);

        for (int i = 0; i < chunks.size(); i++) {
            String chunk = chunks.get(i);
            List<Double> embedding = embeddingService.generateEmbedding(chunk);
            String chunkId = fileName + "-" + i;
            qdrantService.saveChunk(chunkId, chunk, embedding);
        }
    }

    private List<String> chunkText(String text, int maxLength) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        int length = text.length();

        while (start < length) {
            int end = Math.min(length, start + maxLength);
            if (end < length) {
                int lastSpace = text.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }
            String chunk = text.substring(start, end).trim();
            if (!chunk.isEmpty()) {
                chunks.add(chunk);
            }
            start = end;
        }

        return chunks;
    }

    private void saveFile(MultipartFile file)
            throws IOException 
            {

        Files.createDirectories(uploadPath);

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(file.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    private void saveExtractedText(
        String fileName,
        String text)
        throws IOException {

        Files.createDirectories(extractedPath);

        Files.writeString(
                extractedPath.resolve(
                    fileName + ".txt"
                ),
                text
        );
    }
}