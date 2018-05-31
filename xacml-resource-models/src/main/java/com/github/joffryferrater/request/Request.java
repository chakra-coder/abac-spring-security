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
	private AccessSubjectCategory accessSubjectCategory = new AccessSubjectCategory();

	@JsonProperty("Action")
	private ActionCategory actionCategory = new ActionCategory();
	
	@JsonProperty("Environment")
	private EnvironmentCategory environmentCategory = new EnvironmentCategory();
	
	@JsonProperty("Resource")
	private ResourceCategory resourceCategory = new ResourceCategory();

	public Request addAccessSubjectCategory(AccessSubjectCategory accessSubject) {
		this.accessSubjectCategory.addAllAttributes(accessSubject.getAttributes());
		return this;
	}
	
	public Request withAccessSubjectCategory(AccessSubjectCategory accessSubject) {
		this.accessSubjectCategory = accessSubject;
		return this;
	}

	public Request addActionCategory(ActionCategory action) {
    	this.actionCategory.addAllAttributes(action.getAttributes());
		return this;
	}
	
	public Request withActionCategory(ActionCategory action) {
		this.actionCategory = action;
		return this;
	}
	
	public Request addEnvironmentCategory(EnvironmentCategory environment) {
		this.environmentCategory.addAllAttributes(environment.getAttributes());
		return this;
	}
	
	public Request withEnvironmentCategory(EnvironmentCategory environment) {
		this.environmentCategory = environment;
		return this;
	}
	
	public Request addResourceCategory(ResourceCategory resource) {
		this.resourceCategory.addAllAttributes(resource.getAttributes());
		return this;
	}
	
	public Request withResourceCategory(ResourceCategory resource) {
		this.resourceCategory = resource;
		return this;
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