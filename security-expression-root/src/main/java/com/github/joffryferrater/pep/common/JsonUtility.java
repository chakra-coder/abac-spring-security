package com.github.joffryferrater.pep.common;

import com.github.joffryferrater.response.PDPResponse;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    private static ObjectMapper pdpResponseReader = new ObjectMapper();

    private JsonUtility(){}

    static {
        pdpResponseReader.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        pdpResponseReader.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    static PDPResponse getPDPResponse(String responseBody) throws IOException {
        return pdpResponseReader.readValue(responseBody, PDPResponse.class);
    }
}
