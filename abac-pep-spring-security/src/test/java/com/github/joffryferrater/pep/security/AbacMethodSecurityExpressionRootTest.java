package com.github.joffryferrater.pep.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.joffryferrater.pep.PepApplication;
import com.github.joffryferrater.pep.TestBase;
import com.github.joffryferrater.pep.client.PdpClient;
import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.Attribute;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.ResourceCategory;
import com.github.joffryferrater.response.Result;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PepApplication.class, initializers  = ConfigFileApplicationContextInitializer.class)
public class AbacMethodSecurityExpressionRootTest extends TestBase {

    private AbacMethodSecurityExpressionRoot target;

    @Autowired
    PdpClient pdpClient;

    @Before
    public void setUp() {
        target = new AbacMethodSecurityExpressionRoot(new AuthenticationImpl(), pdpClient) {

            @Override
            public boolean hasAccessToResource(String attributeId, List<Object> values) {
                return super.hasAccessToResource(attributeId, values);
            }

            @Override
            protected Optional<List<AccessSubjectCategory>> addAccessSubjectCategoryRequest() {
                AccessSubjectCategory accessSubject = new AccessSubjectCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("access-subject-id");
                attribute.setValue(Collections.singletonList("Joffry"));
                accessSubject.setAttributes(Collections.singletonList(attribute));
                return Optional.of(Collections.singletonList(accessSubject));
            }

            @Override
            protected Optional<List<ActionCategory>> addActionCategoryRequest() {
                ActionCategory actionCategory = new ActionCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("action-id");
                attribute.setValue(Collections.singletonList("GET"));
                actionCategory.setAttributes(Collections.singletonList(attribute));
                return Optional.of(Collections.singletonList(actionCategory));
            }

            @Override
            protected Optional<List<EnvironmentCategory>> addEnvironmentCategoryRequest() {
                EnvironmentCategory environmentCategory = new EnvironmentCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("environment-id");
                attribute.setValue(Collections.singletonList("environment-value"));
                environmentCategory.setAttributes(Collections.singletonList(attribute));
                return Optional.of(Collections.singletonList(environmentCategory));
            }

            @Override
            protected Optional<List<ResourceCategory>> addResourceCategoryRequest() {
                ResourceCategory resourceCategory = new ResourceCategory();
                final Attribute attribute = new Attribute();
                attribute.setAttributeId("resource-id");
                attribute.setValue(Collections.singletonList("resource-value"));
                resourceCategory.setAttributes(Collections.singletonList(attribute));
                return Optional.of(Collections.singletonList(resourceCategory));
            }
        };
    }

    @Test
    public void hasAccessShouldReturnTrue() throws JsonProcessingException {
        final Result result = mockResult("Permit");
        setExpectedPdpResponse(result);

        final boolean hasAccess = target.hasAccessToResource("someAttributeId", Collections.singletonList("someValue"));
        assertThat(hasAccess, is(true));
    }

    @Test
    public void shouldVerifyListOfAccessSubjectAttributes() {
        Optional<List<AccessSubjectCategory>> optionalAccessSubjectCategories = target
            .addAccessSubjectCategoryRequest();
        assertThat(optionalAccessSubjectCategories.isPresent(), is(true));
        List<AccessSubjectCategory> accessSubjectCategories = optionalAccessSubjectCategories.get();
        List<Attribute> attributes = accessSubjectCategories.get(0).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("access-subject-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("Joffry"));
    }

    @Test
    public void shouldVerifyListOfActionAttributes() {
        Optional<List<ActionCategory>> optionalActionCategories = target.addActionCategoryRequest();
        assertThat(optionalActionCategories.isPresent(), is(true));
        List<ActionCategory> actionCategories = optionalActionCategories.get();
        List<Attribute> attributes = actionCategories.get(0).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("action-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("GET"));
    }

    @Test
    public void shouldVerifyListOfEnvironmentAttributes() {
        Optional<List<EnvironmentCategory>> optionalEnvironmentCategories = target.addEnvironmentCategoryRequest();
        assertThat(optionalEnvironmentCategories.isPresent(), is(true));
        List<EnvironmentCategory> environmentCategories = optionalEnvironmentCategories.get();
        List<Attribute> attributes = environmentCategories.get(0).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("environment-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("environment-value"));
    }

    @Test
    public void shouldVerifyListOfResourceAttributes() {
        Optional<List<ResourceCategory>> optionalResourceCategories = target.addResourceCategoryRequest();
        assertThat(optionalResourceCategories.isPresent(), is(true));
        List<ResourceCategory> resourceCategories = optionalResourceCategories.get();
        List<Attribute> attributes = resourceCategories.get(0).getAttributes();
        assertThat(attributes.size(), is(1));

        Attribute attribute = attributes.get(0);
        assertThat(attribute.getAttributeId(), is("resource-id"));
        assertThat(attribute.getValue().size(), is(1));
        assertThat(attribute.getValue().get(0), is("resource-value"));
    }

}