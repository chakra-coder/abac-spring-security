package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Attribute {

    @JsonProperty("AttributeId")
    private String attributeId;
    @JsonProperty("Value")
    private Object[] value;
    @JsonProperty("Issuer")
    private String issuer;
    @JsonProperty("DataType")
    private String dataType;
    @JsonProperty("IncludeInResult")
    private boolean includeInResult = false;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public Object[] getValue() {
        return value;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isIncludeInResult() {
        return includeInResult;
    }

    public void setIncludeInResult(boolean includeInResult) {
        this.includeInResult = includeInResult;
    }
}
