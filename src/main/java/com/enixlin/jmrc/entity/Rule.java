package com.enixlin.jmrc.entity;

public class Rule {
    private int	   id;
    private String name;
    private String url;
    private int	   leaf;
    private String panel;
    private String icon;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public int getLeaf() {
	return leaf;
    }

    public void setLeaf(int leaf) {
	this.leaf = leaf;
    }

    public String getPanel() {
	return panel;
    }

    public void setPanel(String panel) {
	this.panel = panel;
    }

    public String getIcon() {
	return icon;
    }

    public void setIcon(String icon) {
	this.icon = icon;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
