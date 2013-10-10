package com.nro.lucene.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nro.lucene.index.FileIndexerException;
import com.nro.lucene.index.IndexItem;
import com.nro.lucene.index.analyser.IndexerSearch;

@Service
public class IndexerService implements IIndexerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexerService.class);
	
	private IndexerSearch indexerSearch = new IndexerSearch();
	
	  /* (non-Javadoc)
	 * @see com.nro.lucene.service.IIndexerService#createIndex(java.lang.String, java.lang.String, boolean)
	 */
	 public void createIndex(String docsPath, String indexPath, boolean create) {

	    final File docDir = new File(docsPath);
	    if (!docDir.exists() || !docDir.canRead()) {
	      LOGGER.debug("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
	      System.exit(0);
	    }
	    
	    Date start = new Date();
	    try {
	      LOGGER.debug("Indexing to directory '" + indexPath + "'.");

	      Directory dir = FSDirectory.open(new File(indexPath));
	      Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
	      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);

	      if (create) {
	        // Create a new index in the directory, removing any
	        // previously indexed documents:
	        iwc.setOpenMode(OpenMode.CREATE);
	      } else {
	        // Add new documents to an existing index:
	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	      }

	      // Optional: for better indexing performance, if you
	      // are indexing many documents, increase the RAM
	      // buffer.  But if you do this, increase the max heap
	      // size to the JVM (eg add -Xmxm or -Xmxg):
	     //
	      // iwc.setRAMBufferSizeMB(.);

	      IndexWriter writer = new IndexWriter(dir, iwc);
	      indexDocs(writer, docDir);

	      // NOTE: if you want to maximize search performance,
	      // you can optionally call forceMerge here.  This can be
	      // a terribly costly operation, so generally it's only
	      // worth it when your index is relatively static (ie
	      // you're done adding documents to it):
	      //
	      // writer.forceMerge();

	      writer.close();

	      Date end = new Date();
	      LOGGER.debug(end.getTime() - start.getTime() + " total milliseconds");

	    } catch (IOException e) {
	      LOGGER.debug(" caught a " + e.getClass() +
	       "\n with message: " + e.getMessage());
	    }
	  }
	

	/**
	 * Indexes the given file using the given writer, or if a directory is
	 * given, recurses over files and directories found under the given
	 * directory.
	 * 
	 * NOTE: This method indexes one document per input file. This is slow. For
	 * good throughput, put multiple documents into your input file(s). An
	 * example of this is in the benchmark module, which can create "line doc"
	 * files, one document per line, using the <a href=
	 * "./././././contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
	 * >WriteLineDocTask</a>.
	 * 
	 * @param writer
	 *            Writer to the index where the given file/dir info will be
	 *            stored
	 * @param file
	 *            The file to index, or the directory to recurse into to find
	 *            files to index
	 * @throws IOException
	 *             If there is a low-level I/O error
	 */
	private void indexDocs(IndexWriter writer, File file) throws IOException {
		// do not try to index files that cannot be read
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				// an IO error could occur
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {

				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					// at least on windows, some temporary files raise this
					// exception with an "access denied" message
					// checking if the file can be read doesn't help
					return;
				}

				try {

					// make a new, empty document
					Document doc = new Document();

					IndexItem index = indexerSearch.doIndex(file);
					if(index==null) {
						return;
					}

					// Add the path of the file as a field named "path". Use a
					// field that is indexed (i.e. searchable), but don't
					// tokenize
					// the field into separate words and don't index term
					// frequency
					// or positional information:
					Field pathField = new StringField("path", file.getPath(),
							Field.Store.YES);
					doc.add(pathField);

					Field nameField = new StringField("name", index.getFilename(),
							Field.Store.YES);
					doc.add(nameField);

					
					// Add the last modified date of the file a field named
					// "modified".
					// Use a LongField that is indexed (i.e. efficiently
					// filterable with
					// NumericRangeFilter). This indexes to milli-second
					// resolution, which
					// is often too fine. You could instead create a number
					// based on
					// year/month/day/hour/minutes/seconds, down the resolution
					// you require.
					// For example the long value would mean
					// February , , - PM.
					doc.add(new LongField("modified", file.lastModified(),
							Field.Store.NO));

					// Add the contents of the file to a field named "contents".
					// Specify a Reader,
					// so that the text of the file is tokenized and indexed,
					// but not stored.
					// Note that FileReader expects the file to be in UTF-
					// encoding.
					// If that's not the case searching for special characters
					// will fail.
					// doc.add(new TextField("contents", new BufferedReader(new
					// InputStreamReader(fis, "UTF-"))));

					doc.add(new TextField("contents", new BufferedReader(
							new StringReader(index.getContent()))));

					if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
						// New index, so we just add the document (no old
						// document can be there):
						LOGGER.debug("adding " + file);
						writer.addDocument(doc);
					} else {
						// Existing index (an old copy of this document may have
						// been indexed) so
						// we use updateDocument instead to replace the old one
						// matching the exact
						// path, if present:
						LOGGER.debug("updating " + file);
						writer.updateDocument(new Term("path", file.getPath()),
								doc);
					}
				} catch (FileIndexerException e) {
					e.printStackTrace();
				} finally {
					fis.close();
				}
			}
		}
	}

}
