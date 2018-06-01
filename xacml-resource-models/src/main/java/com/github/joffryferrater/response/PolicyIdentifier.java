package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PolicyIdentifier {

    @JsonProperty(value = "PolicyIdReference")
    private List<IdReference> policyIdReference = new ArrayList<>();

    @JsonProperty(value = "PolicySetIdReference")
    private List<IdReference> policySetIdReference = new ArrayList<>();

    public List<IdReference> getPolicyIdReference() {
        return policyIdReference;
    }

    public void setPolicyIdReference(List<IdReference> policyIdReference) {
        this.policyIdReference = policyIdReference;
    }

    public List<IdReference> getPolicySetIdReference() {
        return policySetIdReference;
    }

    public void setPolicySetIdReference(List<IdReference> policySetIdReference) {
        this.policySetIdReference = policySetIdReference;
    }

}
