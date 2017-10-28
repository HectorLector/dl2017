package org.stiazla;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FileHandler {
  static List<File> writeExtractedTextToFiles(Map<File, String> extractedTexts) {

    List<File> outputFiles = new ArrayList<>();
    for(Map.Entry<File, String> entry : extractedTexts.entrySet()){
      String fileOutPath = getOutFilePath(entry.getKey());
      File outFile = new File(fileOutPath);
      try {
        FileUtils.writeStringToFile(outFile, entry.getValue(), Constants.ENCODING);
        outputFiles.add(outFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return outputFiles;
  }

  private static String getOutFilePath(File file) {
    return (Constants.TXT_OUT_DIR +
        File.separator +
        FilenameUtils.removeExtension(file.getName()) +
        Constants.TXT_FILE_EXTENSION);
  }
}
