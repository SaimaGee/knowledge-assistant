package com.saima.ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saima.ai.service.PdfService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final PdfService pdfService;

    @PostMapping("/upload")
    public String uploadPdf(
        @RequestParam("file") MultipartFile file)
        throws Exception {
            return pdfService.extractText(file);
        }
}