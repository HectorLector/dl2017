package org.stiazla;

import java.io.File;
import java.util.Map;

public class Main {
  public static void main(String[] args){

    System.out.println("Hello Digital Libraries WS 17/18");
    if (args == null || args.length != 1) {

      System.out.println("Usage: java -jar dl2017.jar <FolderPathWithPdfFiles>");

    }else {

      String folderPath = args[0].trim();
      File folder = new File(folderPath);
      if(folder.isDirectory() && folder.canRead()){
        Map<File, String> extractedTexts = PdfTextExtractor.extractAllText(folder);
        FileHandler.writeExtractedTextToFiles(extractedTexts);
      } else{
        System.out.println("Cannot read from directory: " + folderPath);
      }
    }
  }
}
