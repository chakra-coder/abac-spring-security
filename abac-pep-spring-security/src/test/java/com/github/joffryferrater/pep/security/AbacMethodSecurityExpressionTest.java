package com.github.joffryferrater.pep.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.joffryferrater.pep.PepApplication;
import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.Category;
import com.github.joffryferrater.request.ResourceCategory;
import com.github.joffryferrater.response.Result;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PepApplication.class, initializers  = ConfigFileApplicationContextInitializer.class)
public class AbacMethodSecurityExpressionTest extends TestBase {

    private AbacMethodSecurityExpression target;

    @Autowired
    PdpClient pdpClient;

    @Before
    public void setUp() {
        target = new AbacMethodSecurityExpression(pdpClient){};
    }

    @Test
    public void evaluateShouldReturnTrue() throws JsonProcessingException {
        final Result mockResult = mockResult("Permit");
        setExpectedPdpResponse(mockResult);

        final boolean result = target.evaluate(categories());
        assertThat(result, is(true));
    }

    private Category[] categories() {
        ResourceCategory resourceCategory = new ResourceCategory();
        final Attribute attribute = new Attribute();
        attribute.setAttributeId("Attributes.resource.endpoint");
        attribute.setValue(Collections.singletonList("helloWorld"));
        resourceCategory.setAttributes(Collections.singletonList(attribute));
        AccessSubjectCategory accessSubjectCategory = new AccessSubjectCategory();
        Attribute attribute2 = new Attribute();
        attribute2.setAttributeId("urn:oasis:names:tc:xacml:1.0:subject:subject-id");
        attribute2.setValue(Collections.singletonList("user"));
        accessSubjectCategory.setAttributes(Collections.singletonList(attribute2));
        return new Category[]{resourceCategory, accessSubjectCategory};
    }
}