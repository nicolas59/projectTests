package com.nro.lucene.index.analyser;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nro.lucene.index.FileIndexerException;
import com.nro.lucene.index.IndexItem;

public class TextIndexer extends AbstractIndexer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TextIndexer.class);

	public List<String> getSupportedMimeType() {
		return Arrays.asList("text/plain");
	}

	@Override
	public IndexItem doIndex(File file) throws FileIndexerException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);

			byte[] data = new byte[1024];
			int length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((length = fis.read(data)) > 0) {
				out.write(data, 0, length);
			}
			return new IndexItem(file.hashCode(), file.getName(),
					out.toString());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			close(fis);
		}
		return null;
	}
	
	private void close(Closeable stream){
		if(stream!=null){
			try {
				stream.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

}
