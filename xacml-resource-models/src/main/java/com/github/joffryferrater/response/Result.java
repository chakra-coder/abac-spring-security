package com.github.joffryferrater.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
public class Result {

    @JsonProperty(value = "Decision")
    private String decision;

    @JsonProperty(value = "Status")
    private Status status;

    @JsonProperty(value = "Obligations")
    private List<ObligationOrAdvice> obligations;

    @JsonProperty(value = "AssociatedAdvice")
    private List<ObligationOrAdvice> advice;

    @JsonProperty(value = "Category")
    private List<Attributes> attributes;

    @JsonProperty(value = "PolicyIdentifierList")
    private List<PolicyIdentifier> policyIdentifierList;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ObligationOrAdvice> getObligations() {
        return obligations;
    }

    public void setObligations(List<ObligationOrAdvice> obligations) {
        this.obligations = obligations;
    }

    public List<ObligationOrAdvice> getAdvice() {
        return advice;
    }

    public void setAdvice(List<ObligationOrAdvice> advice) {
        this.advice = advice;
    }

    public List<Attributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attributes> attributes) {
        this.attributes = attributes;
    }

    public List<PolicyIdentifier> getPolicyIdentifierList() {
        return policyIdentifierList;
    }

    public void setPolicyIdentifierList(List<PolicyIdentifier> policyIdentifierList) {
        this.policyIdentifierList = policyIdentifierList;
    }
}
