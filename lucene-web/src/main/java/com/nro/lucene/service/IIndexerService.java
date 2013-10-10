package com.nro.lucene.service;

public interface IIndexerService {

	/** Index all text files under a directory. */
	void createIndex(String docsPath, String indexPath,
			boolean create);

}