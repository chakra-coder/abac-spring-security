package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PDPResponse {

	@JsonProperty("Response")
	private Response response;;

	@JsonProperty("Response")
	public Response getResponse() {
		return response;
	}

	@JsonProperty("Response")
	public void setResponse(Response response) {
		this.response = response;
	}

}