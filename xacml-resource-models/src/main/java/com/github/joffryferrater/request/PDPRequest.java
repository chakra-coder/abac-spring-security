package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Request" })
public class PDPRequest {

	@JsonProperty("Request")
	private final Request request;;

	public PDPRequest(Request request) {
		this.request = request;
	}

	public Request getRequest() {
		return request;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(request).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof PDPRequest) == false) {
			return false;
		}
		PDPRequest rhs = ((PDPRequest) other);
		return new EqualsBuilder().append(request, rhs.request).isEquals();
	}

}