package com.nro.lucene.index;

import java.io.File;
import java.io.IOException;

public interface FileIndexer {
	public IndexItem index(File file) throws FileIndexerException;
}
