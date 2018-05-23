package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status {

	@JsonProperty("StatusCode")
	private StatusCode statusCode;

	@JsonProperty("StatusCode")
	public StatusCode getStatusCode() {
		return statusCode;
	}

	@JsonProperty("StatusCode")
	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

}
