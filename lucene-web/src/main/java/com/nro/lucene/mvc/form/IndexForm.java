package com.nro.lucene.mvc.form;

public class IndexForm {

	/**
	 * Nom du projet
	 */
	private String projectName;
	
	/**
	 * Repertoire à indexer.
	 */
	private String dir;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
}
