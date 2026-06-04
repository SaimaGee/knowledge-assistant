package com.saima.ai.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

    private final Path uploadPath;
    private final Path extractedPath;

    public PdfService(
            @Value("${pdf.upload-dir:uploads}") String uploadDir,
            @Value("${pdf.extracted-dir:uploads/extracted}") String extractedDir) {
        this.uploadPath = Paths.get(uploadDir);
        this.extractedPath = Paths.get(extractedDir);
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