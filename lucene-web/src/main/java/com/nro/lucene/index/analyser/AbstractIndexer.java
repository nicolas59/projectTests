package com.nro.lucene.index.analyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMimeKeys;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.nro.lucene.index.FileIndexer;
import com.nro.lucene.index.FileIndexerException;
import com.nro.lucene.index.IndexItem;

public abstract class AbstractIndexer implements FileIndexer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractIndexer.class);

	public abstract List<String> getSupportedMimeType();
	
	public boolean isSupported(File file){
		boolean state = false;
		String mimeType = getMinmeType(file);
		if(mimeType!=null && getSupportedMimeType().contains(mimeType)){
			state = true;
		}
		return state;
	}
	
	public final IndexItem index(File file) throws FileIndexerException {
		if(isSupported(file)) {
			LOGGER.debug("Indexation en cours pour Fchier {} ", file.getName());
			return doIndex(file);
		}
		return null;
	}
	
	public abstract IndexItem doIndex(File file) throws FileIndexerException;

	public String getMinmeType(File file) {
		String mimeType = null;
		Parser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();
		ParseContext ctx = new ParseContext();
		BodyContentHandler handler = new BodyContentHandler();

		try {
			parser.parse(new FileInputStream(file), handler, metadata, ctx);
			mimeType = metadata.get("Content-Type");
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (SAXException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (TikaException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return mimeType;
	}

}
