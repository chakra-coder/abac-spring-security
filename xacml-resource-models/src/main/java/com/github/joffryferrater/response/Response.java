package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
public class Response {

	@JsonProperty("Response")
	private List<Result> results;

	public Response() {
		//Empty constructor needed by Jackson
	}

	public Response(List<Result> results) {
		this.results = results;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
}