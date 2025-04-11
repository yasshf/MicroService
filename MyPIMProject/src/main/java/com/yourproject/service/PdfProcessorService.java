package com.yourproject.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class PdfProcessorService {

    public List<String> processPdf(String pdfPath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return splitTextIntoChunks(text);
        }
    }

    private List<String> splitTextIntoChunks(String text) {
        return Arrays.asList(text.split("\\n\\n+"));
    }
}