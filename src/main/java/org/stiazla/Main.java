package org.stiazla;

import java.io.File;
import java.util.List;
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

        //1.) Extract Text from PDF
        Map<File, String> extractedTexts = PdfTextExtractor.extractAllText(folder);
        //2.) Save Text files in extracted directory
        List<File> textFiles = FileHandler.writeExtractedTextToFiles(extractedTexts);
        //3.) Index Text Files with Lucene
        int indexSize = IndexHandler.indexFiles(textFiles);
        System.out.println(String.format("INDEX CREATED in %s, SIZE = %d", Constants.INDEX_OUT_DIR, indexSize));
      } else{
        System.out.println("Cannot read from directory: " + folderPath);
      }
    }
  }
}
