package com.nro.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Main {

	public static void searchIndex(String searchString) throws IOException,
			ParseException {
		System.out.println("Searching for '" + searchString + "'");
		Directory directory = FSDirectory.open(new File(
				"C:/Users/gfiuser/workspace/test-ui/index"));

		IndexReader reader = DirectoryReader.open(directory);
		
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(Version.LUCENE_44, "contents",
				new SimpleAnalyzer(Version.LUCENE_44));
		Query query = parser.parse(searchString);

		TopDocs topDocs = searcher.search(query, 1000);

		ScoreDoc[] hits = topDocs.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println(d.get("path"));
		}

		System.out.println("Found " + hits.length);

	}

	public static void main(String[] args) throws Exception {
		Main.searchIndex("WebSphere MQ");
	}

}
