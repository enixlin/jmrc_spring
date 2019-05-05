package com.enixlin.jmrc.entity;

import java.util.ArrayList;

public class Feature {

	private String name;
	private String url;
	private String panel;
	private int id;
	private int parent_id;
	public ArrayList<Feature> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Feature> children) {
		this.children = children;
	}
	private String icon;
	private boolean  leaf;
	
	private ArrayList<Feature> children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPanel() {
		return panel;
	}
	public void setPanel(String panel) {
		this.panel = panel;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
