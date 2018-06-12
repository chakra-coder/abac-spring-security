package com.github.joffryferrater.pep.security;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.response.Response;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public abstract class AbacWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbacWebSecurityExpressionRoot.class);

    private PdpClient pdpClient;

    public AbacWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, PdpClient pdpClient) {
        super(a, fi);
        this.pdpClient = pdpClient;
    }

    public boolean hasAccessToPath(String attributeId, List<Object> values) {
        PDPRequest pdpRequest = new PDPRequest();
        final Response response;
        try {
            response = pdpClient.sendXacmlJsonRequest(pdpRequest);
            return isPermitted(response);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    /**
     * Override this method to process other properties from {@link Response}
     *
     * @param response The {@link Response} from the pdp server
     * @return true if current user is permitted
     */
    protected boolean isPermitted(Response response) {
        final boolean isPermitted = "Permit".equals(response.getDecision());
        LOGGER.debug("isPermitted: {}", isPermitted);
        return isPermitted;
    }
}
