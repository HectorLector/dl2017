package org.stiazla;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

class SearchHandler {

  static void searchIndex(String indexDir, String queryStr, int maxHits) throws Exception {

    Directory directory = FSDirectory.open(Paths.get(indexDir));

    IndexReader reader = DirectoryReader.open(directory);
    IndexSearcher searcher = new IndexSearcher(reader);

    QueryParser parser = new QueryParser(Constants.CONTENTS, new SimpleAnalyzer());

    Query query = parser.parse(queryStr);

    TopDocs topDocs = searcher.search(query, maxHits);

    ScoreDoc[] hits = topDocs.scoreDocs;
    long totalHits = topDocs.totalHits;
    System.out.println(String.format("Found %d total hits for: '%s' in the following files:", totalHits, queryStr));

    for (ScoreDoc hit : hits) {
      int docId = hit.doc;
      Document d = searcher.doc(docId);
      System.out.println(d.get(Constants.FILE_NAME));
    }

    System.out.println("Found " + hits.length);

  }

}
