package org.stiazla;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PdfTextExtractor {

  public static Map<File, String> extractAllText(File folder){

    Map<File, String> extractedTexts = new HashMap<>();

    for (final File fileEntry : folder.listFiles()) {
      if (fileEntry.isFile() &&
          fileEntry.getName().toLowerCase().endsWith(Constants.PDF_FILE_EXTENSION)) {

        System.out.println("Extracting text from: " + fileEntry.getAbsolutePath());

        String text = extractText(fileEntry);
        extractedTexts.put(fileEntry, text);
      } else {
        System.out.println("Ignoring file/directory: " + fileEntry.getAbsolutePath());
      }
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
