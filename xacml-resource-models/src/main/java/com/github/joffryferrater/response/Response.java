package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	@JsonProperty("Decision")
	private String decision;
	@JsonProperty("Status")
	private Status status;

	@JsonProperty("Decision")
	public String getDecision() {
		return decision;
	}

	@JsonProperty("Decision")
	public void setDecision(String decision) {
		this.decision = decision;
	}

	@JsonProperty("Status")
	public Status getStatus() {
		return status;
	}

	@JsonProperty("Status")
	public void setStatus(Status status) {
		this.status = status;
	}

}