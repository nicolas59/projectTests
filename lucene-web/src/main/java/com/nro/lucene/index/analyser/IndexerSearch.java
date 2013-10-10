package com.nro.lucene.index.analyser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nro.lucene.index.FileIndexer;
import com.nro.lucene.index.FileIndexerException;
import com.nro.lucene.index.IndexItem;

public class IndexerSearch  {
	
	private List<FileIndexer> indexers = new ArrayList<FileIndexer>();

	public IndexerSearch(){
		indexers.add( new MSDocumentIndexer());
		indexers.add(new TextIndexer());
	}
	
	public IndexItem doIndex(File file)  throws FileIndexerException{
		IndexItem item = null; 
		
		for(FileIndexer indexer : indexers){
			item = indexer.index(file);
			if(item!=null){
				break;
			}
		}
		
		
		return item;
	}
	
}
