package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.request.Request;
import com.github.joffryferrater.request.Resource;
import com.github.joffryferrater.response.PDPResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public abstract class XacmlMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
    MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    private PdpClient pdpClient;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public XacmlMethodSecurityExpressionRoot(Authentication authentication, PdpClient pdpClient) {
        super(authentication);
        this.pdpClient = pdpClient;
    }

    public boolean hasAccess(String attributeId, List<Object> values) {
        final PDPRequest pdpRequest = createResourceAttributeRequest(attributeId, values);
        try {
            final PDPResponse pdpResponse = pdpClient.sendXacmlJsonRequest(pdpRequest);
            return isPermitted(pdpResponse);
        } catch (URISyntaxException | MalformedURLException e) {
            //TO DO: Replace with Logger
            e.printStackTrace();
        }
        return false;
    }

    private PDPRequest createResourceAttributeRequest(String attributeId, List<Object> values) {
        Attribute attribute = new Attribute();
        attribute.setAttributeId(attributeId);
        attribute.setValue(values);
        Resource resource = new Resource();
        resource.addAttribute(attribute);
        Request request = new Request();
        request.addResource(resource);
        PDPRequest pdpRequest = new PDPRequest();
        pdpRequest.setRequest(request);
        return pdpRequest;
    }

    private boolean isPermitted(PDPResponse pdpResponse) {
        return "Permit".equals(pdpResponse.getResponse().getDecision());
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
}
