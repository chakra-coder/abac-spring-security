package com.github.joffryferrater.pep.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.joffryferrater.response.Response;
import java.io.IOException;
import org.junit.Test;

public class JsonUtilityTest {

    @Test
    public void shouldDeserializePdpResponse() throws IOException {
        final String responseInString = getPdpResponse();
        Response response = JsonUtility.getPDPResponse(responseInString);

        assertThat(response.getResults().get(0).getDecision(), is("NotApplicable"));
    }

    private String getPdpResponse() {
        return "{\"Response\":[{\n"
            + "\t\"Decision\": \"NotApplicable\",\n"
            + "\t\"Status\": {\n"
            + "\t\t\"StatusCode\": {\n"
            + "\t\t\t\"Value\": \"urn:oasis:names:tc:xacml:1.0:status:ok\",\n"
            + "\t\t\t\"StatusCode\": []\n"
            + "\t\t}\n"
            + "\t},\n"
            + "\t\"Obligations\": [],\n"
            + "\t\"AssociatedAdvice\": [],\n"
            + "\t\"Category\": []\n"
            + "}]}";
    }
}