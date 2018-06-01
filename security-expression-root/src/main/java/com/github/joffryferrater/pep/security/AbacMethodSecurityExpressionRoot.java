package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.request.Request;
import com.github.joffryferrater.request.ResourceCategory;
import com.github.joffryferrater.response.Response;
import java.io.IOException;
import java.util.List;
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
        final PDPRequest pdpRequest = getAllCategoriesRequest(attributeId, values);
        try {
            final Response pdpResponse = pdpClient.sendXacmlJsonRequest(pdpRequest);
            return isPermitted(pdpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    private PDPRequest getAllCategoriesRequest(String attributeId, List<Object> values) {
        Request request = createResourceCategoryRequest(attributeId, values);
        addCategoriesToRequest(request);
        PDPRequest pdpRequest = new PDPRequest();
        pdpRequest.setRequest(request);
        return pdpRequest;
    }


    private boolean isPermitted(Response response) {
        final boolean isPermitted = "Permit".equals(response.getDecision());
        LOGGER.debug("isPermitted: {}", isPermitted);
        return isPermitted;
    }

    private Request createResourceCategoryRequest(String attributeId, List<Object> values) {
        Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        ResourceCategory resource = new ResourceCategory();
        resource.addAttribute(attribute);
        Request request = new Request();
        request.addResourceCategory(resource);
        return request;
    }

    private void addCategoriesToRequest(Request request) {
        request.addAccessSubjectCategory(addAccessSubjectCategoryRequest());
        request.addActionCategory(addActionCategoryRequest());
        request.addEnvironmentCategory(addEnvironmentCategoryRequest());
        request.addResourceCategory(addResourceCategoryRequest());
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

    protected AccessSubjectCategory addAccessSubjectCategoryRequest() {
        return new AccessSubjectCategory();
    }

    protected ActionCategory addActionCategoryRequest() {
        return new ActionCategory();
    }

    protected EnvironmentCategory addEnvironmentCategoryRequest() {
        return new EnvironmentCategory();
    }

    protected ResourceCategory addResourceCategoryRequest() {
        return new ResourceCategory();
    }
}
