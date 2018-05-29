package com.github.joffryferrater.pep.client;

import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.response.PDPResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PdpClient {

    private final RestTemplate restTemplate;
    private final PdpConfiguration pdpConfiguration;

    @Autowired
    public PdpClient(RestTemplateBuilder restTemplateBuilder, PdpConfiguration pdpConfiguration) {
        this.restTemplate = restTemplateBuilder.build();
        this.pdpConfiguration = pdpConfiguration;
    }

    public PDPResponse sendXacmlJsonRequest(PDPRequest pdpRequest) throws URISyntaxException, MalformedURLException {
        HttpHeaders headers = createHeaders();
        HttpEntity<PDPRequest> entity = new HttpEntity<>(pdpRequest, headers);
        final String url = pdpConfiguration.getUrl();
        return restTemplate.postForObject(new URL(url).toURI(), entity, PDPResponse.class);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xacml+json");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
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

