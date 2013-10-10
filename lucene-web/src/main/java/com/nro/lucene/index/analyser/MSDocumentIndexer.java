package com.nro.lucene.index.analyser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.tika.parser.microsoft.OfficeParser.POIFSDocumentType;
import org.apache.xmlbeans.XmlException;

import com.nro.lucene.index.FileIndexerException;
import com.nro.lucene.index.IndexItem;

public class MSDocumentIndexer extends AbstractIndexer {

	static List<String> SUPPORTED_MIME = new ArrayList<String>();
	static {
		POIFSDocumentType[] types = POIFSDocumentType.values();
		for (POIFSDocumentType type : types) {
			SUPPORTED_MIME.add(type.getType().toString());

		}
	}

	@Override
	public List<String> getSupportedMimeType() {
		return SUPPORTED_MIME;
	}

	@Override
	public IndexItem doIndex(File file) throws FileIndexerException {
		try {
			String content = ExtractorFactory.createExtractor(file).getText();
			return new IndexItem((long) file.hashCode(), file.getName(),
					content);
		} catch (InvalidFormatException e) {
			throw new FileIndexerException(e);
		} catch (OpenXML4JException e) {
			throw new FileIndexerException(e);
		} catch (XmlException e) {
			throw new FileIndexerException(e);
		} catch (IOException e) {
			throw new FileIndexerException(e);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}