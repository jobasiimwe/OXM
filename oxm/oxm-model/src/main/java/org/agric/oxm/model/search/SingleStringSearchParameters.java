package org.agric.oxm.model.search;

public class SingleStringSearchParameters {

	private String guery;

	public SingleStringSearchParameters() {
	}

	public SingleStringSearchParameters(String query) {
		this.setQuery(query);
	}

	public String getQuery() {
		return guery;
	}

	public void setQuery(String query) {
		this.guery = query;
	}

}
