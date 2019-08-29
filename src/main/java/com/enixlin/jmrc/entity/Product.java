/**
 * 
 */
package com.enixlin.jmrc.entity;

import java.io.Serializable;

/**
 * 国际业务产品实体类
 * 
 * @author linzhenhuan
 *
 */
public class Product implements Serializable {

	private int id;
	public String name;
	private String description;
	private boolean settleRange;

	public boolean isSettleRange() {
		return settleRange;
	}

	public void setSettleRange(boolean settleRange) {
		this.settleRange = settleRange;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
