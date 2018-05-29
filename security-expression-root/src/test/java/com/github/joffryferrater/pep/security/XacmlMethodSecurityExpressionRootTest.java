package com.github.joffryferrater.pep.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.response.PDPResponse;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class XacmlMethodSecurityExpressionRootTest extends TestBase {

    private XacmlMethodSecurityExpressionRoot target;

    @Autowired
    PdpClient pdpClient;

    @Before
    public void setUp(){
        target = new XacmlMethodSecurityExpressionRoot(new AuthenticationImpl(), pdpClient) {
            @Override
            public boolean hasAccessToResource(String attributeId, List<Object> values) {
                return super.hasAccessToResource(attributeId, values);
            }
        };
    }

    @Test
    public void hasAccessShouldReturnTrue() throws JsonProcessingException {
        final PDPResponse pdpResponse = mockPdpResponse("Permit");
        setExpectedPdpResponse(pdpResponse);

        final boolean hasAccess = target.hasAccessToResource("someAttributeId", Collections.singletonList("someValue"));
        assertThat(hasAccess, is(true));
    }
}