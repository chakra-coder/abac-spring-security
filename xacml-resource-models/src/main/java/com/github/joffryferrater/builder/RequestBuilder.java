package com.github.joffryferrater.builder;

import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.request.Request;
import com.github.joffryferrater.request.ResourceCategory;

public class RequestBuilder {

    private AccessSubjectCategory accessSubjectCategory;
    private ActionCategory actionCategory;
    private ResourceCategory resourceCategory;
    private EnvironmentCategory environmentCategory;

    public RequestBuilder(AccessSubjectCategory accessSubjectCategory,
        ActionCategory actionCategory, ResourceCategory resourceCategory,
        EnvironmentCategory environmentCategory) {
        this.accessSubjectCategory = accessSubjectCategory;
        this.actionCategory = actionCategory;
        this.resourceCategory = resourceCategory;
        this.environmentCategory = environmentCategory;
    }

    public RequestBuilder(AccessSubjectCategory accessSubjectCategory) {
        this(accessSubjectCategory, null, null, null);
    }

    public RequestBuilder(ActionCategory actionCategory) {
        this(null, actionCategory, null, null);
    }

    public RequestBuilder(ResourceCategory resourceCategory) {
        this(null, null, resourceCategory, null);
    }

    public RequestBuilder(EnvironmentCategory environmentCategory) {
        this(null, null, null, environmentCategory);
    }

    public RequestBuilder addAccessSubjectCategory(AccessSubjectCategory accessSubject) {
        this.accessSubjectCategory.addAllAttributes(accessSubject.getAttributes());
        return this;
    }

    public RequestBuilder withAccessSubjectCategory(AccessSubjectCategory accessSubject) {
        this.accessSubjectCategory = accessSubject;
        return this;
    }

    public RequestBuilder addActionCategory(ActionCategory action) {
        this.actionCategory.addAllAttributes(action.getAttributes());
        return this;
    }

    public RequestBuilder withActionCategory(ActionCategory action) {
        this.actionCategory = action;
        return this;
    }

    public RequestBuilder addEnvironmentCategory(EnvironmentCategory environment) {
        this.environmentCategory.addAllAttributes(environment.getAttributes());
        return this;
    }

    public RequestBuilder withEnvironmentCategory(EnvironmentCategory environment) {
        this.environmentCategory = environment;
        return this;
    }

    public RequestBuilder addResourceCategory(ResourceCategory resource) {
        this.resourceCategory.addAllAttributes(resource.getAttributes());
        return this;
    }

    public RequestBuilder withResourceCategory(ResourceCategory resource) {
        this.resourceCategory = resource;
        return this;
    }

    public AccessSubjectCategory getAccessSubjectCategory() {
        return accessSubjectCategory;
    }

    public ActionCategory getActionCategory() {
        return actionCategory;
    }

    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public EnvironmentCategory getEnvironmentCategory() {
        return environmentCategory;
    }

    public PDPRequest build() {
        final Request request = new Request(accessSubjectCategory, actionCategory, environmentCategory,
            resourceCategory);
        return new PDPRequest(request);
    }
}
