package com.enixlin.jmrc.policydocument;

public class PolicyFileEntity {
	
	public int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int wordsCount;
	public String docNum;
	public String link;
	public String sTitle;
	public String title;
	public String postMechanism;
	public String content;
	public int tid;
	public double postTime;
	public double effTime;
	public int state;
	public int dId;
	public int cId;
	public Object [] attaches;
	public PolicyFileEntity(int wordsCount, String docNum, String link, String sTitle, String title,
			String postMechanism, String content, int tid, double postTime, double effTime, int state, int dId, int cId,
			Object[] attaches) {
		super();
		this.wordsCount = wordsCount;
		this.docNum = docNum;
		this.link = link;
		this.sTitle = sTitle;
		this.title = title;
		this.postMechanism = postMechanism;
		this.content = content;
		this.tid = tid;
		this.postTime = postTime;
		this.effTime = effTime;
		this.state = state;
		this.dId = dId;
		this.cId = cId;
		this.attaches = attaches;
	}
	public PolicyFileEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getWordsCount() {
		return wordsCount;
	}
	public void setWordsCount(int wordsCount) {
		this.wordsCount = wordsCount;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getsTitle() {
		return sTitle;
	}
	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPostMechanism() {
		return postMechanism;
	}
	public void setPostMechanism(String postMechanism) {
		this.postMechanism = postMechanism;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public double getPostTime() {
		return postTime;
	}
	public void setPostTime(double postTime) {
		this.postTime = postTime;
	}
	public double getEffTime() {
		return effTime;
	}
	public void setEffTime(double effTime) {
		this.effTime = effTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getdId() {
		return dId;
	}
	public void setdId(int dId) {
		this.dId = dId;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public Object[] getAttaches() {
		return attaches;
	}
	public void setAttaches(Object[] attaches) {
		this.attaches = attaches;
	}
	
	
	
	
	
	
	

}
