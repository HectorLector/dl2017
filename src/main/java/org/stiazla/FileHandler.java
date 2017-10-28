package org.stiazla;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

class FileHandler {
  static File writeExtractedTextToFile(File file, String extractedText) {

    String fileOutPath = getOutFilePath(file);
    File outFile = new File(fileOutPath);
    try {
      FileUtils.writeStringToFile(outFile, extractedText, Constants.ENCODING);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return outFile;
  }

  private static String getOutFilePath(File file) {
    return (Constants.TXT_OUT_DIR +
        File.separator +
        FilenameUtils.removeExtension(file.getName()) +
        Constants.TXT_FILE_EXTENSION);
  }

  static void getAllFilesRecursive(File dir, String ext, List<File> result){
    for (final File fileEntry : dir.listFiles()) {
      if(fileEntry.isFile() && fileEntry.getName().toLowerCase().endsWith(ext)){
        result.add(fileEntry);
      }else{
        getAllFilesRecursive(fileEntry, ext, result);
      }
    }
  }
}
