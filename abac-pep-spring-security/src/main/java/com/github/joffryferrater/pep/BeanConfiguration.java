package com.github.joffryferrater.pep;

import com.github.joffryferrater.pep.client.PdpClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PdpConfiguration pdpConfiguration() {
        return new PdpConfiguration();
    }

    @Bean
    public PdpClient pdpClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder.setConnectTimeout(5000);
        final PdpConfiguration pdpConfiguration = pdpConfiguration();
        final String username = pdpConfiguration.getUsername();
        final String password = pdpConfiguration.getPassword();
        if (username != null && password != null) {
            restTemplateBuilder.basicAuthorization(username, password);
        }
        return new PdpClient(restTemplateBuilder, pdpConfiguration());
    }
}

