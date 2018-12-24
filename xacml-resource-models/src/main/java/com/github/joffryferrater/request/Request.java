package com.github.joffryferrater.request;

import static com.github.joffryferrater.request.Constants.ACCESS_SUBJECT;
import static com.github.joffryferrater.request.Constants.ACTION;
import static com.github.joffryferrater.request.Constants.ENVIRONMENT;
import static com.github.joffryferrater.request.Constants.RESOURCE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({
    ACCESS_SUBJECT,
    ACTION,
    RESOURCE,
    ENVIRONMENT
})
public class Request {

    @JsonProperty(ACCESS_SUBJECT)
    private List<AccessSubjectCategory> accessSubjectCategory = new ArrayList<>();

    @JsonProperty(ACTION)
    private List<ActionCategory> actionCategory = new ArrayList<>();

    @JsonProperty(ENVIRONMENT)
    private List<EnvironmentCategory> environmentCategory = new ArrayList<>();

    @JsonProperty(RESOURCE)
    private List<ResourceCategory> resourceCategory = new ArrayList<>();

    public List<AccessSubjectCategory> getAccessSubjectCategory() {
        return accessSubjectCategory;
    }

    public void setAccessSubjectCategory(
        List<AccessSubjectCategory> accessSubjectCategory) {
        this.accessSubjectCategory = new ArrayList<>(accessSubjectCategory);
    }

    public List<ActionCategory> getActionCategory() {
        return actionCategory;
    }

    public void setActionCategory(List<ActionCategory> actionCategory) {
        this.actionCategory = new ArrayList<>(actionCategory);
    }

    public List<EnvironmentCategory> getEnvironmentCategory() {
        return environmentCategory;
    }

    public void setEnvironmentCategory(List<EnvironmentCategory> environmentCategory) {
        this.environmentCategory = new ArrayList<>(environmentCategory);
    }

    public List<ResourceCategory> getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(List<ResourceCategory> resourceCategory) {
        this.resourceCategory = new ArrayList<>(resourceCategory);
    }
}