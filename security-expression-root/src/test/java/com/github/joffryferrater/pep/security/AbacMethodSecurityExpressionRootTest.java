package com.github.joffryferrater.pep.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.ResourceCategory;
import com.github.joffryferrater.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AbacMethodSecurityExpressionRootTest extends TestBase {

    private AbacMethodSecurityExpressionRoot target;

    @Autowired
    PdpClient pdpClient;

    @Before
    public void setUp(){
        target = new AbacMethodSecurityExpressionRoot(new AuthenticationImpl(), pdpClient) {

            @Override
            public boolean hasAccessToResource(String attributeId, List<Object> values) {
                return super.hasAccessToResource(attributeId, values);
            }

            @Override
            protected Optional<AccessSubjectCategory> addAccessSubjectCategoryRequest() {
                AccessSubjectCategory accessSubject = new AccessSubjectCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("access-subject-id");
                attribute.setValue(Collections.singletonList("Joffry"));
                accessSubject.withAttributes(attribute);
                return Optional.of(accessSubject);
            }

            @Override
            protected Optional<ActionCategory> addActionCategoryRequest() {
                ActionCategory actionCategory = new ActionCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("action-id");
                attribute.setValue(Collections.singletonList("GET"));
                actionCategory.withAttributes(attribute);
                return Optional.of(actionCategory);
            }

            @Override
            protected Optional<EnvironmentCategory> addEnvironmentCategoryRequest() {
                EnvironmentCategory environmentCategory = new EnvironmentCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("environment-id");
                attribute.setValue(Collections.singletonList("environment-value"));
                environmentCategory.withAttributes(attribute);
                return Optional.of(environmentCategory);
            }

            @Override
            protected Optional<ResourceCategory> addResourceCategoryRequest() {
                ResourceCategory resourceCategory = new ResourceCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("resource-id");
                attribute.setValue(Collections.singletonList("resource-value"));
                resourceCategory.withAttributes(attribute);
                return Optional.of(resourceCategory);
            }
        };
    }

    @Test
    public void hasAccessShouldReturnTrue() throws JsonProcessingException {
        final Response pdpResponse = mockPdpResponse("Permit");
        setExpectedPdpResponse(pdpResponse);

        final boolean hasAccess = target.hasAccessToResource("someAttributeId", Collections.singletonList("someValue"));
        assertThat(hasAccess, is(true));
    }

    @Test
    public void shouldVerifyListOfAccessSubjectAttributes() {
        Optional<AccessSubjectCategory> accessSubject = target.addAccessSubjectCategoryRequest();
        List<Attribute> attributes = accessSubject.orElse(new AccessSubjectCategory()).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("access-subject-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("Joffry"));
    }

    @Test
    public void shouldVerifyListOfActionAttributes() {
        Optional<ActionCategory> actionCategory = target.addActionCategoryRequest();
        List<Attribute> attributes = actionCategory.orElse(new ActionCategory()).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("action-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("GET"));
    }

    @Test
    public void shouldVerifyListOfEnvironmentAttributes() {
        Optional<EnvironmentCategory> environmentCategory = target.addEnvironmentCategoryRequest();
        List<Attribute> attributes = environmentCategory.orElse(new EnvironmentCategory()).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("environment-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("environment-value"));
    }

    @Test
    public void shouldVerifyListOfResourceAttributes() {
        Optional<ResourceCategory> resourceCategory = target.addResourceCategoryRequest();
        List<Attribute> attributes = resourceCategory.orElse(new ResourceCategory()).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("resource-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("resource-value"));
    }

}