package com.github.joffryferrater.pep.client;

import com.github.joffryferrater.pep.common.JsonUtility;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.request.XacmlRequest;
import com.github.joffryferrater.response.Response;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PdpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdpClient.class);

    private final RestTemplate restTemplate;
    private PdpConfiguration pdpConfiguration;

    public PdpClient(RestTemplateBuilder restTemplateBuilder, PdpConfiguration pdpConfiguration) {
        this.restTemplate = restTemplateBuilder.build();
        this.pdpConfiguration = pdpConfiguration;
    }

    public Response sendXacmlJsonRequest(XacmlRequest xacmlRequest)
        throws IOException {
        HttpHeaders headers = createHeaders();
        HttpEntity<XacmlRequest> entity = new HttpEntity<>(xacmlRequest, headers);
        final String url = pdpConfiguration.getAuthorizeEndpoint();
        final String pdpResponse = restTemplate.postForObject(url, entity, String.class);
        LOGGER.debug("Pdp Response: {}", pdpResponse);
        return JsonUtility.getPDPResponse(pdpResponse);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xacml+json");
        headers.add("Accept", "application/json");
        final Optional<String> base64EncodedCredentials = getBase64EncodedCredentials();
        if (base64EncodedCredentials.isPresent()) {
            headers.add("Authorization", "Basic " + base64EncodedCredentials.get());
        }
        return headers;
    }

    private Optional<String> getBase64EncodedCredentials() {
        final String username = pdpConfiguration.getUsername();
        final String password = pdpConfiguration.getPassword();
        if (username == null || password == null) {
            return Optional.empty();
        }
        return Optional.of(Base64.getEncoder()
            .encodeToString((username + ":" + password).getBytes()));
    }
}

