package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"AccessSubject",
	"Action",
	"Resource",
	"Environment"
})
public class Request {

	@JsonProperty("AccessSubject")
	private AccessSubjectCategory accessSubjectCategory;

	@JsonProperty("Action")
	private ActionCategory actionCategory;
	
	@JsonProperty("Environment")
	private EnvironmentCategory environmentCategory;
	
	@JsonProperty("Resource")
	private ResourceCategory resourceCategory;

	public Request(AccessSubjectCategory accessSubjectCategory,
		ActionCategory actionCategory, EnvironmentCategory environmentCategory,
		ResourceCategory resourceCategory) {
		this.accessSubjectCategory = accessSubjectCategory;
		this.actionCategory = actionCategory;
		this.environmentCategory = environmentCategory;
		this.resourceCategory = resourceCategory;
	}

	public AccessSubjectCategory getAccessSubjectCategory() {
		return accessSubjectCategory;
	}

	public ActionCategory getActionCategory() {
		return actionCategory;
	}

	public EnvironmentCategory getEnvironmentCategory() {
		return environmentCategory;
	}


	public ResourceCategory getResourceCategory() {
		return resourceCategory;
	}

}