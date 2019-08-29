/**
 * 
 */
package com.enixlin.jmrc.entity;

import java.math.BigDecimal;

/**
 * 分月明细实体类
 * 
 * @author linzhenhuan
 *
 */
public class MonthPerformace {

	private String name;
	private BigDecimal performance;
	private int times;
	private String description;



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

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
