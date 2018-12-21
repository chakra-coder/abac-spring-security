package com.github.joffryferrater.pep.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.joffryferrater.response.Response;
import java.io.IOException;

public class JsonUtility {

    private static ObjectMapper pdpResponseReader = new ObjectMapper();

    private JsonUtility(){}

    static {
        pdpResponseReader.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
        pdpResponseReader.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        pdpResponseReader.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Response getPDPResponse(String responseBody) throws IOException {
        System.out.println(responseBody);
        return pdpResponseReader.readValue(responseBody, Response.class);
    }
}
