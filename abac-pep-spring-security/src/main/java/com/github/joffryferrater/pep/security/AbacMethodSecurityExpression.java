package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.Category;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.Request;
import com.github.joffryferrater.request.ResourceCategory;
import com.github.joffryferrater.request.XacmlRequest;
import com.github.joffryferrater.response.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbacMethodSecurityExpression {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbacMethodSecurityExpression.class);

    private PdpClient pdpClient;

    public AbacMethodSecurityExpression(PdpClient pdpClient) {
        this.pdpClient = pdpClient;
    }

    public boolean evaluate(Category ...categories) {
        List<ResourceCategory> resourceCategories = new ArrayList<>();
        List<AccessSubjectCategory> accessSubjectCategories = new ArrayList<>();
        List<ActionCategory> actionCategories = new ArrayList<>();
        List<EnvironmentCategory> environmentCategories = new ArrayList<>();
        for(Category category : categories) {
            if(category instanceof ResourceCategory) {
                ResourceCategory resourceCategory = (ResourceCategory) category;
                resourceCategories.add(resourceCategory);
            }
            if(category instanceof AccessSubjectCategory) {
                AccessSubjectCategory accessSubjectCategory = (AccessSubjectCategory) category;
                accessSubjectCategories.add(accessSubjectCategory);
            }
            if(category instanceof ActionCategory) {
                ActionCategory actionCategory = (ActionCategory) category;
                actionCategories.add(actionCategory);
            }
            if(category instanceof EnvironmentCategory) {
                EnvironmentCategory environmentCategory = (EnvironmentCategory) category;
                environmentCategories.add(environmentCategory);
            }

        }
        Request request = buildRequest(resourceCategories, accessSubjectCategories, actionCategories,
            environmentCategories);
        final Response pdpResponse = sendAuthorizationRequest(request);
        return isPermitted(pdpResponse);
    }

    public ResourceCategory resourceAttribute(String attributeId, List<Object> values){
        ResourceCategory resourceCategory = new ResourceCategory();
        final Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        resourceCategory.setAttributes(Collections.singletonList(attribute));
        return resourceCategory;
    }

    public ActionCategory actionAttribute(String attributeId, List<Object> values) {
        ActionCategory actionCategory = new ActionCategory();
        final Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        actionCategory.setAttributes(Collections.singletonList(attribute));
        return actionCategory;
    }

    public AccessSubjectCategory accessSubjectAttribute(String attributeId, List<Object> values) {
        AccessSubjectCategory accessSubjectCategory = new AccessSubjectCategory();
        Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        accessSubjectCategory.setAttributes(Collections.singletonList(attribute));
        return accessSubjectCategory;
    }

    public EnvironmentCategory environmentAttribute(String attributeId, List<Object> values) {
        EnvironmentCategory environmentCategory = new EnvironmentCategory();
        Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        environmentCategory.setAttributes(Collections.singletonList(attribute));
        return environmentCategory;
    }

    private Request buildRequest(List<ResourceCategory> resourceCategories,
        List<AccessSubjectCategory> accessSubjectCategories, List<ActionCategory> actionCategories,
        List<EnvironmentCategory> environmentCategories) {
        Request request = new Request();
        request.setResourceCategory(resourceCategories);
        request.setAccessSubjectCategory(accessSubjectCategories);
        request.setActionCategory(actionCategories);
        request.setEnvironmentCategory(environmentCategories);
        return request;
    }

    private Response sendAuthorizationRequest(Request request) {
        XacmlRequest xacmlRequest = new XacmlRequest(request);
        return pdpClient.sendXacmlJsonRequest(xacmlRequest);
    }

    private boolean isPermitted(Response response) {
        final boolean isPermitted = "Permit".equals(response.getResults().get(0).getDecision());
        LOGGER.debug("isPermitted: {}", isPermitted);
        return isPermitted;
    }
}
