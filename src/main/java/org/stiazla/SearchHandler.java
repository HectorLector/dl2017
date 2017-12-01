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
import java.util.ArrayList;
import java.util.List;

class SearchHandler {

  static List<String> searchIndex(String indexDir, String queryStr, int maxHits) throws Exception {

    List<String> results = new ArrayList<>();

    Directory directory = FSDirectory.open(Paths.get(indexDir));

    IndexReader reader = DirectoryReader.open(directory);
    IndexSearcher searcher = new IndexSearcher(reader);

    QueryParser parser = new QueryParser(Constants.CONTENTS, new SimpleAnalyzer());

    Query query = parser.parse(queryStr);

    TopDocs topDocs = searcher.search(query, maxHits);

    ScoreDoc[] hits = topDocs.scoreDocs;
    long totalHits = topDocs.totalHits;

    for (ScoreDoc hit : hits) {
      int docId = hit.doc;
      Document d = searcher.doc(docId);
      String result = String.format("File: %s; Score: %f", d.get(Constants.FILE_NAME), hit.score);
      results.add(result);
    }

    System.out.println(String.format("Found %d total hits for: '%s' in the following files:", totalHits, queryStr));
    return results;
  }

}
