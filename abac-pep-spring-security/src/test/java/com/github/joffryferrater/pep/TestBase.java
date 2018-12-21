package com.github.joffryferrater.pep;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.response.Response;
import com.github.joffryferrater.response.Result;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(PdpClient.class)
public class TestBase {

    private static final String ADMIN = "admin";
    private static final String PASSWORD = "password";

    @Autowired
    private PdpConfiguration configuration;

    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;

    protected Result mockResult(String decision) {
        Result pdpResponse = new Result();
        pdpResponse.setDecision(decision);
        return pdpResponse;
    }

    protected void setExpectedPdpResponse(Result result) throws JsonProcessingException {
        Response pdpResponse = new Response(Collections.singletonList(result));
        String responseInString = objectMapper.writeValueAsString(pdpResponse);
        this.server.expect(requestTo(configuration.getAuthorizeEndpoint()))
            .andExpect(method(HttpMethod.POST))
            .andExpect(header("Content-Type", "application/xacml+json"))
            //YWRtaW46cGFzc3dvcmQ= is base64 encoded of admin:password
            .andExpect(header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ="))
            .andRespond(withSuccess(responseInString, MediaType.APPLICATION_JSON));
    }

    public class AuthenticationImpl implements Authentication {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return PASSWORD;
        }

        @Override
        public Object getDetails() {
            return ADMIN;
        }

        @Override
        public Object getPrincipal() {
            return ADMIN;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return ADMIN;
        }
    }

}
