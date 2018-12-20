package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
public class Attributes {

    @JsonProperty(value = "CategoryId")
    private String categoryId;
    @JsonProperty(value = "Id")
    private String id;
    @JsonProperty(value = "Content")
    private String content;
    @JsonProperty(value = "Attribute")
    List<Attribute> attributes = new ArrayList<>();

    public Attributes() {
        //Empty constructor for Jackson
    }

    public Attributes(String categoryId, String id, String content,
        List<Attribute> attributes) {
        this.categoryId = categoryId;
        this.id = id;
        this.content = content;
        this.attributes = attributes;
    }

    public Attributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
