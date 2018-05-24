package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "AttributeId", "Value", "DataType" })
public class Attribute {

	@JsonProperty("AttributeId")
	private String attributeId;
	@JsonProperty("Value")
	private List<Object> value = new ArrayList<>();
	@JsonProperty("DataType")
	private String dataType;

	@JsonProperty("AttributeId")
	public String getAttributeId() {
		return attributeId;
	}

	@JsonProperty("AttributeId")
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public Attribute withAttributeId(String attributeId) {
		this.attributeId = attributeId;
		return this;
	}

	@JsonProperty("Value")
	public Collection<Object> getValue() {
		return value;
	}

	@JsonProperty("Value")
	public void setValue(List<Object> value) {
		this.value = value;
	}

	public Attribute withValues(List<Object> value) {
		this.value = value;
		return this;
	}

	public Attribute withValues(Object value) {
		this.value.add(value);
		return this;
	}
	
	@JsonProperty("DataType")
	public String getDataType() {
		return dataType;
	}

	@JsonProperty("DataType")
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Attribute withDataType(String dataType) {
		this.dataType = dataType;
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(attributeId).append(value).append(dataType).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Attribute) == false) {
			return false;
		}
		Attribute rhs = ((Attribute) other);
		return new EqualsBuilder().append(attributeId, rhs.attributeId).append(value, rhs.value)
				.append(dataType, rhs.dataType).isEquals();
	}

}