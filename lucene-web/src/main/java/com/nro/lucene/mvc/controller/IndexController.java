package com.nro.lucene.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nro.lucene.mvc.form.IndexForm;
import com.nro.lucene.service.IIndexerService;
import com.nro.lucene.service.IndexerService;

@Controller
public class IndexController {
	
	@Autowired
	private IIndexerService indexerService;
	
	@RequestMapping(value = "/lancerIndex", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("indexer") IndexForm indexForm,
			RedirectAttributes result) {

		indexerService.createIndex(indexForm.getDir(), "C:/Users/gfiuser/workspace/test-ui/index",  false);

		return "redirect:indexer.html";
	}
	
	@RequestMapping(value = "/indexer", method = RequestMethod.GET)
	public String helloWorld(@ModelAttribute("indexer") IndexForm indexForm) {
		return "indexer";

	}
}
