/**
 * 
 */
package com.enixlin.jmrc.entity;

import java.math.BigInteger;

/**
 * 报表类型名称实体类
 * @author linzhenhuan
 *
 */
public class Report {

	private BigInteger id;
	private String name;
	private BigInteger pid;
	
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getpId() {
		return pid;
	}
	public void setpId(BigInteger pId) {
		this.pid = pId;
	}
	
}
