package com.github.joffryferrater.pep.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pdp.server")
@Component
public class PdpConfiguration {

	private String authorizeEndpoint;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorizeEndpoint() {
		return authorizeEndpoint;
	}

	public void setAuthorizeEndpoint(String authorizeEndpoint) {
		this.authorizeEndpoint = authorizeEndpoint;
	}
	
	
}
