package com.saima.ai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChunkingService {

    public List<String> chunkText(
            String text,
            int maxLength) {

        List<String> chunks = new ArrayList<>();

        int start = 0;
        int length = text.length();

        while (start < length) {

            int end =
                    Math.min(
                            length,
                            start + maxLength);

            if (end < length) {

                int lastSpace =
                        text.lastIndexOf(
                                ' ',
                                end);

                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            String chunk =
                    text.substring(
                            start,
                            end)
                            .trim();

            if (!chunk.isEmpty()) {
                chunks.add(chunk);
            }

            start = end;
        }

        return chunks;
    }
}