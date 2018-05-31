package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Attribute" })
public abstract class Category {

	@JsonProperty("Attribute")
	private List<Attribute> attribute = new ArrayList<>();

	@JsonProperty("Attribute")
	public List<Attribute> getAttributes() {
		return attribute;
	}

	public Category addAttribute(Attribute attribute) {
		this.attribute.add(attribute);
		return this;
	}
	
	public Category addAllAttributes(List<Attribute> attributes) {
		this.attribute.addAll(attributes);
		return this;
	}
	
	public Category withAttributes(List<Attribute> attribute) {
		this.attribute = attribute;
		return this;
	}

	public Category withAttributes(Attribute attribute) {
		this.attribute.add(attribute);
		return this;
	}
}
