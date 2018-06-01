package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeAssignment {

    @JsonProperty("AttributeId")
    private URI attributeId;

    @JsonProperty("Value")
    private Object value;

    @JsonProperty("Category")
    private URI category;

    @JsonProperty("DataType")
    private URI dataType;

    @JsonProperty("Issuer")
    private String issuer;

    public URI getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(URI id) {
        attributeId = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public URI getCategory() {
        return category;
    }

    public void setCategory(URI category) {
        this.category = category;
    }

    public URI getDataType() {
        return dataType;
    }

    public void setDataType(URI dataType) {
        this.dataType = dataType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

}