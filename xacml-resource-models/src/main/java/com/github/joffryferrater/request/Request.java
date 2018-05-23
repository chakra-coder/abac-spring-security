package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "AccessSubject" })
public class Request {

	@JsonProperty("AccessSubject")
	private AccessSubject accessSubject;

	@JsonProperty("Action")
	private Action action;
	
	@JsonProperty("Environment")
	private Environment environment;
	
	@JsonProperty("Resource")
	private Resource resource;

	public AccessSubject getAccessSubject() {
		return accessSubject;
	}
	
	public Request addAccessSubject(AccessSubject accessSubject) {
		if(this.accessSubject == null) {
			this.accessSubject = accessSubject;
		} else {
			this.accessSubject.addAllAttributes(accessSubject.getAttributes());
		}
		return this;
	}
	
	public Request withAccessSubject(AccessSubject accessSubject) {
		this.accessSubject = accessSubject;
		return this;
	}

	public Request addActionAttribute(Action action) {
		if(this.action == null) {
			this.action = action;
		} else {
			this.action.addAllAttributes(action.getAttributes());
		}
		return this;
	}
	
	public Request withAction(Action action) {
		this.action = action;
		return this;
	}
	
	public Request addEnvironment(Environment environment) {
		if(this.environment == null) {
			this.environment = environment;
		} else {
			this.environment.addAllAttributes(environment.getAttributes());
		}
		return this;
	}
	
	public Request withEnvironment(Environment environment) {
		this.environment = environment;
		return this;
	}
	
	public Request addResource(Resource resource) {
		if(this.resource == null) {
			this.resource = resource;
		} else {
			this.resource.addAllAttributes(resource.getAttributes());
		}
		return this;
	}
	
	public Request withResource(Resource resource) {
		this.resource = resource;
		return this;
	}
	
	public Action getAction() {
		return action;
	}

	public Environment getEnvironment() {
		return environment;
	}


	public Resource getResource() {
		return resource;
	}

}