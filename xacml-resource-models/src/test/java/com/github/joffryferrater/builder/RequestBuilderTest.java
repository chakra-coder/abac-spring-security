package com.github.joffryferrater.builder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.github.joffryferrater.request.AccessSubjectCategory;
import com.github.joffryferrater.request.ActionCategory;
import com.github.joffryferrater.request.EnvironmentCategory;
import com.github.joffryferrater.request.ResourceCategory;
import org.junit.Test;

public class RequestBuilderTest {

    @Test
    public void shouldBuildAccessSubjectCategory() {
        AccessSubjectCategory accessSubjectCategory = new AccessSubjectCategory();
        RequestBuilder requestBuilder = new RequestBuilder(accessSubjectCategory);

        assertThat(requestBuilder.getAccessSubjectCategory(), is(notNullValue()));
    }

    @Test
    public void shouldBuildActionCategoy() {
        ActionCategory actionCategory = new ActionCategory();
        RequestBuilder requestBuilder = new RequestBuilder(actionCategory);

        assertThat(requestBuilder.getActionCategory(), is(notNullValue()));
    }

    @Test
    public void shouldBuildResourceCategoy() {
        ResourceCategory resourceCategory = new ResourceCategory();
        RequestBuilder requestBuilder = new RequestBuilder(resourceCategory);

        assertThat(requestBuilder.getResourceCategory(), is(notNullValue()));
    }

    @Test
    public void shouldBuildEnvironmentCategory() {
        EnvironmentCategory environmentCategory = new EnvironmentCategory();
        RequestBuilder requestBuilder = new RequestBuilder(environmentCategory);

        assertThat(requestBuilder.getEnvironmentCategory(), is(notNullValue()));
    }
}