package com.github.joffryferrater.request;

public class ActionCategory extends Category {

    @Override
    public String getCategoryId() {
        return "urn:oasis:names:tc:xacml:3.0:attribute-category:action";
    }
}
