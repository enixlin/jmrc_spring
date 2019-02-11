
package com.enixlin.jmrc.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class PolicyDocumentNote {
	 private int id;
	 private int dId;
	 private String content;
	 private int user_id;
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
		return user_id;
	}
	public void setUserId(int userId) {
		this.user_id = userId;
	}
	public Timestamp getModify() {
		return modify;
	}
	public void setModify(Timestamp modify) {
		this.modify = modify;
	}
	

}
