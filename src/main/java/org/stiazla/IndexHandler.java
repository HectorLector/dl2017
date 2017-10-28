package org.stiazla;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

class IndexHandler {
  static int indexFiles(List<File> textFiles) {

    System.out.println("START CREATING INDEX...");

    int result = 0;
    try
    {
      IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
      config.setUseCompoundFile(false);
      config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
      config.setCodec(new SimpleTextCodec());

      Directory luceneDir = FSDirectory.open(Paths.get(Constants.INDEX_OUT_DIR));
      IndexWriter indexWriter = new IndexWriter(luceneDir, config);
      for(File file : textFiles){
        try{

          if(file.isFile()){
            Document doc = getDocument(file);
            indexWriter.addDocument(doc);
          }
        }catch(Exception e){
          e.printStackTrace();
        }
      }

      result = indexWriter.maxDoc();
      indexWriter.close();

    }catch(Exception e){
      e.printStackTrace();
    }

    return result;
  }


  private static Document getDocument(File file) throws IOException {

    Document doc = new Document();
    doc.add(new TextField(Constants.CONTENTS, new FileReader(file)));
    doc.add(new StringField(Constants.FILE_NAME, file.getName(), Field.Store.YES));

    return doc;
  }

}
