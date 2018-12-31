package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.Request;
import com.github.joffryferrater.request.ResourceCategory;
import com.github.joffryferrater.request.XacmlRequest;
import com.github.joffryferrater.response.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbacMethodSecurityExpression {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbacMethodSecurityExpression.class);

    private PdpClient pdpClient;

    public AbacMethodSecurityExpression(PdpClient pdpClient) {
        this.pdpClient = pdpClient;
    }

    public boolean hasAccessToResource(String attributeId, List<Object> values) {
        LOGGER.debug("Entering hasAccessToResource(attributeId={},values={}", attributeId, values);
        final Request request = getAllCategoriesRequest(attributeId, values);
        final XacmlRequest xacmlRequest = new XacmlRequest(request);
        final Response pdpResponse = pdpClient.sendXacmlJsonRequest(xacmlRequest);
        return isPermitted(pdpResponse);
    }

    private Request getAllCategoriesRequest(String attributeId, List<Object> values) {
        Request request = new Request();
        ResourceCategory resourceCategory = createResourceCategoryRequest(attributeId, values);
        List<ResourceCategory> resourceCategories = new ArrayList<>();
        resourceCategories.add(resourceCategory);
        final Optional<List<ResourceCategory>> optionalResourceCategories = addResourceCategoryRequest();
        optionalResourceCategories.ifPresent(resourceCategories::addAll);
        request.setResourceCategory(resourceCategories);
        final Optional<List<ActionCategory>> actionCategories = addActionCategoryRequest();
        actionCategories.ifPresent(request::setActionCategory);
        final Optional<List<EnvironmentCategory>> environmentCategories = addEnvironmentCategoryRequest();
        environmentCategories.ifPresent(request::setEnvironmentCategory);
        final Optional<List<AccessSubjectCategory>> accessSubjectCategories = addAccessSubjectCategoryRequest();
        accessSubjectCategories.ifPresent(request::setAccessSubjectCategory);
        return request;
    }

    private boolean isPermitted(Response response) {
        final boolean isPermitted = "Permit".equals(response.getResults().get(0).getDecision());
        LOGGER.debug("isPermitted: {}", isPermitted);
        return isPermitted;
    }

    private ResourceCategory createResourceCategoryRequest(String attributeId, List<Object> values) {
        Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        ResourceCategory resourceCategory = new ResourceCategory();
        resourceCategory.setAttributes(Collections.singletonList(attribute));
        return resourceCategory;
    }

    protected Optional<List<AccessSubjectCategory>> addAccessSubjectCategoryRequest() {
        return Optional.empty();
    }

    protected Optional<List<ActionCategory>> addActionCategoryRequest() {
        return Optional.empty();
    }

    protected Optional<List<EnvironmentCategory>> addEnvironmentCategoryRequest() {
        return Optional.empty();
    }

    protected Optional<List<ResourceCategory>> addResourceCategoryRequest() {
        return Optional.empty();
    }
}
