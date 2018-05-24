package com.github.joffryferrater.pep;

import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import com.github.joffryferrater.request.PDPRequest;
import com.github.joffryferrater.response.PDPResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
        final String url = pdpConfiguration.getUrl();
        return restTemplate.postForObject(new URL(url).toURI(), pdpRequest, PDPResponse.class);
    }
}

