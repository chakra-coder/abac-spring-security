package com.github.joffryferrater.pep.security;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.response.Response;
import java.io.IOException;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterInvocation;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AbacWebSecurityExpressionRootTest extends TestBase {

    private AbacWebSecurityExpressionRoot target;

    @Autowired
    private PdpClient pdpClient;

    @Mock
    private FilterInvocation filterInvocation;

    @Before
    public void setUp() {
        target = new AbacWebSecurityExpressionRoot(new AuthenticationImpl(), filterInvocation, pdpClient) {
            @Override
            public boolean hasIpAddress(String ipAddress) {
                return super.hasIpAddress(ipAddress);
            }
        };
    }


    @Test
    public void hasAccessToPathShouldReturnTrue() throws IOException {
        final Response pdpResponse = mockPdpResponse("Permit");
        setExpectedPdpResponse(pdpResponse);

        final String attributeId = "Attributes.resource.path";
        boolean hasAccessToPath = target.hasAccessToPath(attributeId, Collections.singletonList("/somePath"));

        assertThat(hasAccessToPath, is(true));
    }
}