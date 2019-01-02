package com.github.joffryferrater.pep.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.joffryferrater.pep.BeanConfiguration;
import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.Request;
import com.github.joffryferrater.request.XacmlRequest;
import com.github.joffryferrater.response.Response;
import com.github.joffryferrater.response.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BeanConfiguration.class, initializers  = ConfigFileApplicationContextInitializer.class)
public class PdpClientTest extends TestBase {

    @Autowired
    private PdpClient target;

    @Before
    public void setUp() throws JsonProcessingException {
        Result result = mockResult("Permit");
        setExpectedPdpResponse(result);
    }

    @Test
    public void shouldReturnPdpResponse() throws IOException {
        Request request = new Request();
        List<AccessSubjectCategory> accessSubjectCategoryList = new ArrayList<>();
        AccessSubjectCategory accessSubject = new AccessSubjectCategory();
        final Attribute attribute = new Attribute();
        attribute.setAttributeId("access-subject-id");
        attribute.setValue(Collections.singletonList("Joffry"));
        accessSubject.setAttributes(Collections.singletonList(attribute));
        accessSubjectCategoryList.add(accessSubject);
        request.setAccessSubjectCategory(accessSubjectCategoryList);
        Response response = target.sendXacmlJsonRequest(new XacmlRequest(request));

        assertThat(response.getResults().get(0).getDecision(), is("Permit"));
    }

}