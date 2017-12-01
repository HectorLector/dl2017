package org.stiazla;

import java.io.File;
import java.util.List;

public class Main {
  public static void main(String[] args){

    System.out.println("Hello Digital Libraries WS 17/18");
    if (args == null || args.length != 3) {

      System.out.println("Usage: java -jar dl2017.jar <folderWithPdfPath> <CreateIndex> <searchquery>");
      System.out.println("Example: java -jar dl2017.jar /home/user/pdf/ yes Simpsons");
    }else {

      String folderPath = args[0].trim();
      String createIndex = args[1].trim().toLowerCase();
      String searchQuery = args[2].trim();
      //Check for "yes" or "ja"
      boolean shouldCreateIndex = createIndex.startsWith("y") || createIndex.startsWith("j");
      
      File folder = new File(folderPath);
      if(folder.isDirectory() && folder.canRead()){

        if(shouldCreateIndex){
          //1.) Extract Text from PDF and Save them in Output directory
          List<File> textFiles = PdfTextExtractor.extractAllText(folder);

          //2.) Index Text Files with Lucene
          int indexSize = IndexHandler.indexFiles(textFiles);
          System.out.println(String.format("INDEX CREATED in %s, SIZE = %d", Constants.INDEX_OUT_DIR, indexSize));

        }

        //new DigLib("DigLib");

        //3.) Search
        try {
          List<String> results = SearchHandler.searchIndex(Constants.INDEX_OUT_DIR, searchQuery, 1000);

          //Print Results
          System.out.println(String.join(System.lineSeparator(), results));
        } catch (Exception e) {
          e.printStackTrace();
        }

      } else{
        System.out.println("Cannot read from directory: " + folderPath);
      }
    }
  }
}
