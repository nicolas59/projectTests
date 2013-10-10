package com.nro.lucene.mvc.form;

public class SearchForm {

	/**
	 * Mot recherché
	 */
	private String words;
	
	/**
	 * Nom du projet
	 */
	private String projectName;

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
