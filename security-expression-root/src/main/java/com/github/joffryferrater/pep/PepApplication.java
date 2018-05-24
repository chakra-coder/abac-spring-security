package com.github.joffryferrater.pep;

import com.github.joffryferrater.pep.configuration.PdpConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PepApplication {

    public static void main(String[] args) {
        SpringApplication.run(PepApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PdpConfiguration pdpConfiguration() {
        PdpConfiguration pdpConfiguration = new PdpConfiguration();
        pdpConfiguration.setUrl("http://localhost:8081/asm-pdp/authorize");
        return pdpConfiguration;
    }
}

