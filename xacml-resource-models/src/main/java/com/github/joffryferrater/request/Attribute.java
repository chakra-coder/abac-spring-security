package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({ "AttributeId", "Value", "DataType" })
public class Attribute {

	@JsonProperty("AttributeId")
	private String attributeId;
	@JsonProperty("Value")
	private List<Object> value = new ArrayList<>();
	@JsonProperty("DataType")
	private String dataType;

	public Attribute() {
		//Empty constructor for Jackson
	}

	public Attribute(String attributeId, List<Object> value, String dataType) {
		this.attributeId = attributeId;
		this.value = value;
		this.dataType = dataType;
	}

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}