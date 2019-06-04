
package com.enixlin.jmrc.entity;

import java.sql.Timestamp;

public class PolicyDocumentNote {
	 private int id;
	 private int dId;
	 private String title;
	 private String docNum;
	 public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String content;
	 private String name;
	 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int userId;
	 private Timestamp modify;
	 private boolean overWrite; //是否要覆盖原有的笔记
	 
	public int getId() {
		return id;
	}
	public boolean isOverWrite() {
		return overWrite;
	}
	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getdId() {
		return dId;
	}
	public PolicyDocumentNote() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setdId(int dId) {
		this.dId = dId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getModify() {
		return modify;
	}
	public void setModify(Timestamp modify) {
		this.modify = modify;
	}
	

}
