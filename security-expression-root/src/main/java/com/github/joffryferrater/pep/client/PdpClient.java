package com.github.joffryferrater.pep.client;

import com.github.joffryferrater.pep.common.JsonUtility;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.response.Response;
import java.io.IOException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PdpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdpClient.class);

    private final RestTemplate restTemplate;
    @Autowired
    private PdpConfiguration pdpConfiguration;

    @Autowired
    public PdpClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Response sendXacmlJsonRequest(PDPRequest pdpRequest)
        throws IOException {
        LOGGER.debug("Sending pdp request : {}", pdpRequest);
        HttpHeaders headers = createHeaders();
        HttpEntity<PDPRequest> entity = new HttpEntity<>(pdpRequest, headers);
        LOGGER.debug("http headers: {}", entity.getHeaders());
        LOGGER.debug("http requestBody: {}", entity.getBody());
        final String url = pdpConfiguration.getAuthorizeEndpoint();
        final String pdpResponse = restTemplate.postForObject(url, entity, String.class);
        LOGGER.debug("Pdp Response: {}", pdpResponse);
        return JsonUtility.getPDPResponse(pdpResponse);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xacml+json");
        final String base64EncodedCredentials = getBase64EncodedCredentials();
        headers.add("Authorization", "Basic " + base64EncodedCredentials);
        return headers;
    }

    private String getBase64EncodedCredentials() {
        final String username = pdpConfiguration.getUsername();
        final String password = pdpConfiguration.getPassword();
        return Base64.getEncoder()
            .encodeToString((username + ":" + password).getBytes());
    }
}

