package com.github.joffryferrater.pep;

import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PepApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PdpConfiguration pdpConfiguration() {
        return new PdpConfiguration();
    }

    @Bean
    public PdpClient pdpClient(RestTemplateBuilder restTemplateBuilder) {
        return new PdpClient(restTemplateBuilder, pdpConfiguration());
    }
}

