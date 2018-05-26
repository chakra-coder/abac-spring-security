package com.github.joffryferrater.pep;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.response.PDPResponse;
import com.github.joffryferrater.response.Response;
import java.util.Collection;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

@RunWith(SpringRunner.class)
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

    public PDPResponse mockPdpResponse(String decision) {
        PDPResponse pdpResponse = new PDPResponse();
        Response response = new Response();
        response.setDecision(decision);
        pdpResponse.setResponse(response);
        return pdpResponse;
    }

    public void setExpectedResponse(PDPResponse pdpResponse) throws JsonProcessingException {
        String responseInString = objectMapper.writeValueAsString(pdpResponse);
        this.server.expect(requestTo(configuration.getUrl())).andRespond(withSuccess(responseInString,
            MediaType.APPLICATION_JSON));
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
