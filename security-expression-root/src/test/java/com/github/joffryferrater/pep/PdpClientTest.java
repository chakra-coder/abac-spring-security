package com.github.joffryferrater.pep;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.response.PDPResponse;
import com.github.joffryferrater.response.Response;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

@RunWith(SpringRunner.class)
@RestClientTest(PdpClient.class)
public class PdpClientTest {

    @Autowired
    private PdpClient target;

    @Autowired
    private PdpConfiguration configuration;

    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws JsonProcessingException {
        PDPResponse pdpResponse = new PDPResponse();
        Response response = new Response();
        response.setDecision("Permit");
        pdpResponse.setResponse(response);
        String responseInString = objectMapper.writeValueAsString(pdpResponse);
        this.server.expect(requestTo(configuration.getUrl())).andRespond(withSuccess(responseInString,
            MediaType.APPLICATION_JSON));
    }

    @Test
    public void contextLoad() {
        assertThat(target, is(notNullValue()));
    }

    @Test
    public void shouldReturnPdpResponse() throws URISyntaxException, MalformedURLException {
        PDPRequest pdpRequest = new PDPRequest();
        PDPResponse response = target.sendXacmlJsonRequest(pdpRequest);

        assertThat(response.getResponse().getDecision(), is("Permit"));
    }

}