package com.enixlin.jmrc.policydocument;

public class HtmlFile {
 private int dId;
 private String html;
public int getDid() {
	return dId;
}
public void setDid(int did) {
	dId = did;
}
public String getHtmlContent() {
	return html;
}
public void setHtmlContent(String htmlContent) {
	html = htmlContent;
}
public HtmlFile() {
	super();
	// TODO Auto-generated constructor stub
}
public HtmlFile(int did, String htmlContent) {
	super();
	dId = did;
	html = htmlContent;
}
 
}
