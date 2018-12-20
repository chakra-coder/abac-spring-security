package com.github.joffryferrater.request;

public class AccessSubjectCategory extends Category {

    @Override
    public String getCategoryId() {
        return "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
    }
}