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
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public abstract class AbacMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
    MethodSecurityExpressionOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbacMethodSecurityExpressionRoot.class);

    private Object filterObject;
    private Object returnObject;

    private PdpClient pdpClient;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     * @param pdpClient the {@link PdpClient}
     */
    public AbacMethodSecurityExpressionRoot(Authentication authentication, PdpClient pdpClient) {
        super(authentication);
        this.pdpClient = pdpClient;
    }

    public boolean hasAccessToResource(String attributeId, List<Object> values) {
        LOGGER.debug("Entering hasAccessToResource(attributeId={},values={}", attributeId, values);
        final Request request = getAllCategoriesRequest(attributeId, values);
        try {
            final Response pdpResponse = pdpClient.sendXacmlJsonRequest(new XacmlRequest(request));
            return isPermitted(pdpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    private Request getAllCategoriesRequest(String attributeId, List<Object> values) {
        ResourceCategory resourceCategory = createResourceCategoryRequest(attributeId, values);
        List<ResourceCategory> resourceCategories = Arrays.asList(resourceCategory);
        Request request = new Request();
        request.setResourceCategory(resourceCategories);
        return request;
    }


    private boolean isPermitted(Response response) {
        final boolean isPermitted = "Permit".equals(response.getDecision());
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

    private AccessSubjectCategory getAccessSubjectCategory() {
        Optional<AccessSubjectCategory> optionalAccessSubjectCategory = addAccessSubjectCategoryRequest();
        return optionalAccessSubjectCategory.orElse(new AccessSubjectCategory());
    }

    private ActionCategory getActionCategory() {
        Optional<ActionCategory> optionalActionCategory = addActionCategoryRequest();
        return  optionalActionCategory.orElse(new ActionCategory());
    }

    private ResourceCategory getResourceCategory() {
        Optional<ResourceCategory> optionalResourceCategory = addResourceCategoryRequest();
        return optionalResourceCategory.orElse(new ResourceCategory());
    }

    private  EnvironmentCategory getEnvironmentCategory() {
        Optional<EnvironmentCategory> optionalEnvironmentCategory = addEnvironmentCategoryRequest();
        return  optionalEnvironmentCategory.orElse(new EnvironmentCategory());
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    protected Optional<AccessSubjectCategory> addAccessSubjectCategoryRequest() {
        return Optional.empty();
    }

    protected Optional<ActionCategory> addActionCategoryRequest() {
        return Optional.empty();
    }

    protected Optional<EnvironmentCategory> addEnvironmentCategoryRequest() {
        return Optional.empty();
    }

    protected Optional<ResourceCategory> addResourceCategoryRequest() {
        return Optional.empty();
    }
}
