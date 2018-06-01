package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ObligationOrAdvice {

    @JsonProperty(value = "Id")
    private URI id;

    @JsonProperty(value = "AttributeAssignment")
    private List<AttributeAssignment> attributeAssignment = new ArrayList<>();

    public URI getId() {
        return id;
    }

    public void setId(URI id) {
        this.id = id;
    }

    public List<AttributeAssignment> getAttributeAssignment() {
        return attributeAssignment;
    }

    public void setAttributeAssignment(List<AttributeAssignment> attributeAssignment) {
        this.attributeAssignment = attributeAssignment;
    }

}