package com.softeoz.bookmarktree.domain;

/**
 * @author jdelouche
 * 
 */
public class Path {

	private static final String PATH_SEPARATOR = "/";
	private String value;

	public void setValue(String path) {
		this.value = path;
	}

	public void add(String name) {
		value += PATH_SEPARATOR + name;
	}

	public String getValue() {

		return value;
	}

}
