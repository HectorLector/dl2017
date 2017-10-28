package org.stiazla;

import java.io.File;
import java.util.List;

public class Main {
  public static void main(String[] args){

    System.out.println("Hello Digital Libraries WS 17/18");
    if (args == null || args.length != 1) {

      System.out.println("Usage: java -jar dl2017.jar <FolderPathWithPdfFiles>");

    }else {

      String folderPath = args[0].trim();
      File folder = new File(folderPath);
      if(folder.isDirectory() && folder.canRead()){

        //1.) Extract Text from PDF and Save them in Output directory
        List<File> textFiles = PdfTextExtractor.extractAllText(folder);

        //2.) Index Text Files with Lucene
        int indexSize = IndexHandler.indexFiles(textFiles);
        System.out.println(String.format("INDEX CREATED in %s, SIZE = %d", Constants.INDEX_OUT_DIR, indexSize));

        //3.) Search
        try {
          SearchHandler.searchIndex(Constants.INDEX_OUT_DIR, "science", 1000);
        } catch (Exception e) {
          e.printStackTrace();
        }

      } else{
        System.out.println("Cannot read from directory: " + folderPath);
      }
    }
  }
}
