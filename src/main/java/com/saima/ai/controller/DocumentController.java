package com.saima.ai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saima.ai.exception.InvalidUploadException;
import com.saima.ai.model.dto.DocumentDto;
import com.saima.ai.service.ConversationService;
import com.saima.ai.service.PdfService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final PdfService pdfService;
    private final ConversationService conversationService;

    @GetMapping
    public List<DocumentDto> listDocuments() {
        return conversationService.listDocuments();
    }

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new InvalidUploadException("Please choose a file to upload.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".pdf")) {
            throw new InvalidUploadException("Only PDF files are supported.");
        }

        UUID documentId = UUID.randomUUID();
        var indexResult = pdfService.extractTextAndIndex(documentId, fileName, file);
        conversationService.registerDocument(documentId, fileName, indexResult.pageCount(), indexResult.chunkCount());

        return "Indexed " + fileName;
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable UUID id) {
        conversationService.deleteDocument(id);
    }
}