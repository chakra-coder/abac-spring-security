package com.github.joffryferrater.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public abstract class Category extends Attributes{

	@Override
	public abstract String getCategoryId();
}
