package com.enixlin.jmrc.entity;

import java.math.BigDecimal;

/**
 * 指标完绩实体类
 * @author linzhenhuan
 *
 */
public class IndexPerformance {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPerformance() {
		return performance;
	}
	public void setPerformance(BigDecimal performance) {
		this.performance = performance;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String name;
	private BigDecimal performance;
	private String date;

}
