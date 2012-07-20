package org.primefaces.examples.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.examples.domain.UrlDocument;

@ManagedBean(name = "displayDoc")
@SessionScoped
public class DisplayController {

	private UrlDocument doc;

	private String name;

	private String url;

	private String fullPath;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setDoc(UrlDocument doc) {
		this.doc = doc;
		name = doc.getName();
		url = doc.getUrl();
		fullPath = doc.getFullPath();
	}

	@Override
	public String toString() {
		return doc.getFullPath();
	}

}
