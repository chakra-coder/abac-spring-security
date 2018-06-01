package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusCode {

    @JsonProperty(value = "Value")
    private URI value;

    @JsonProperty(value = "StatusCode")
    private List<StatusCode> statusCodes = new ArrayList<>();

    public URI getValue() {
        return value;
    }

    public void setValue(URI value) {
        this.value = value;
    }

    public List<StatusCode> getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(List<StatusCode> statusCodes) {
        this.statusCodes = statusCodes;
    }

}