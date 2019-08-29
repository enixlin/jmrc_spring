/**
 * 
 */
package com.enixlin.jmrc.entity;

import java.math.BigDecimal;

/**
 * 任务实体类
 * @author linzhenhuan
 *
 */
public class Task {

	private int id;
	private String name;
	private BigDecimal amount;
	private String description;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
