package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Attribute" })
public abstract class AttributeDefinition {

	@JsonProperty("Attribute")
	private List<Attribute> attribute = new ArrayList<Attribute>();

	@JsonProperty("Attribute")
	public List<Attribute> getAttributes() {
		return attribute;
	}

	public AttributeDefinition addAttribute(Attribute attribute) {
		this.attribute.add(attribute);
		return this;
	}
	
	public AttributeDefinition addAllAttributes(List<Attribute> attributes) {
		this.attribute.addAll(attributes);
		return this;
	}
	
	public AttributeDefinition withAttributes(List<Attribute> attribute) {
		this.attribute = attribute;
		return this;
	}

	public AttributeDefinition withAttributes(Attribute attribute) {
		this.attribute.add(attribute);
		return this;
	}
}
