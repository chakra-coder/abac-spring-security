package com.github.joffryferrater.pep;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.response.Response;
import com.github.joffryferrater.response.Result;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(PdpClient.class)
public class TestBase {

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
            .andRespond(withSuccess(responseInString, MediaType.APPLICATION_JSON));
    }
}
