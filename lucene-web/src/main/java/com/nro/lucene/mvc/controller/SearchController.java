package com.nro.lucene.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nro.lucene.mvc.form.SearchForm;
import com.nro.lucene.mvc.vo.ResultVo;

@Controller
public class SearchController {

	public static List<ResultVo> searchIndex(String searchString) throws IOException,
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
		List<ResultVo> docs = new ArrayList<ResultVo>();
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			
			ResultVo vo = new ResultVo();
			vo.setPath(URLEncoder.encode(d.get("path").replaceAll("\\\\","/")));
			vo.setName(d.get("name"));
			docs.add(vo);
		}
		return docs;
	}

	@RequestMapping(value = "/lancerRecherche", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("recherche") SearchForm contact,
			RedirectAttributes result) {

		try{
			result.addFlashAttribute("results",searchIndex(contact.getWords()));
		}catch(Exception e){
			e.printStackTrace();
		}
		result.addFlashAttribute("word", contact.getWords());

		return "redirect:rechercher.html";
	}

	@RequestMapping(value = "/rechercher", method = RequestMethod.GET)
	public String helloWorld(@ModelAttribute("recherche") SearchForm contact) {
		return "rechercher";

	}
}
