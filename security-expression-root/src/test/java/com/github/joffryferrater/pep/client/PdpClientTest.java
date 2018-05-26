package com.github.joffryferrater.pep.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.response.PDPResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PdpClientTest extends TestBase {

    @Autowired
    private PdpClient target;

    @Before
    public void setUp() throws JsonProcessingException {
        PDPResponse pdpResponse = mockPdpResponse("Permit");
        setExpectedResponse(pdpResponse);
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