package com.github.joffryferrater.pep;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PepConfiguration {

    @Autowired
    private PdpConfiguration pdpConfiguration;

    @Bean
    public PdpConfiguration pdpConfiguration() {
        return new PdpConfiguration();
    }

    @Bean
    public PdpClient pdpClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder.setConnectTimeout(5000);
        final String username = pdpConfiguration.getUsername();
        final String password = pdpConfiguration.getPassword();
        if (username != null && password != null) {
            restTemplateBuilder.basicAuthorization(pdpConfiguration.getUsername(), pdpConfiguration.getPassword());
        }
        return new PdpClient(restTemplateBuilder, pdpConfiguration());
    }
}

