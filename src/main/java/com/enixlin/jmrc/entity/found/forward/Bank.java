package com.enixlin.jmrc.entity.found.forward;
/**
 * 合作银行实体类
 * @author linzhenhuan
 *
 */
public class Bank {

	private int id;
	private String name;
	private String agreement_id;
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
	public String getAgreement_id() {
		return agreement_id;
	}
	public void setAgreement_id(String agreement_id) {
		this.agreement_id = agreement_id;
	}
	
}
