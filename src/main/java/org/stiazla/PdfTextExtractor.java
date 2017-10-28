package org.stiazla;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PdfTextExtractor {

  static List<File> extractAllText(File folder){

    List<File> extractedTexts = new ArrayList<>();

    List<File> allPdfFiles = new ArrayList<>();
    FileHandler.getAllFilesRecursive(folder, Constants.PDF_FILE_EXTENSION, allPdfFiles);

    for (final File fileEntry : allPdfFiles) {

        System.out.println("Extracting text from: " + fileEntry.getAbsolutePath());
        String text = extractText(fileEntry);
        File outFile = FileHandler.writeExtractedTextToFile(fileEntry, text);
        extractedTexts.add(outFile);
    }
    return extractedTexts;
  }

  private static String extractText(File fileEntry) {

    String result = null;
    PDDocument doc = null;
    try {
      doc = PDDocument.load(fileEntry);
      result = new PDFTextStripper().getText(doc);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if(doc != null) doc.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

}
