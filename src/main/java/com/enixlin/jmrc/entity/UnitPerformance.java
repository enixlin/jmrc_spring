package com.enixlin.jmrc.entity;

import java.math.BigDecimal;

/**
 * 经营单位经营实绩实现类
 * 
 * @author linzhenhuan
 *
 */
public class UnitPerformance {

	private String unitName;
	private String unitCode;
	private String taskName;
	private BigDecimal taskAmount;

	private String amount;
	private int times;
	private BigDecimal amount_pre;
	private int times_pre;
	private BigDecimal amount_compare;
	private int times_compare;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public BigDecimal getTaskAmount() {
		return taskAmount;
	}

	public void setTaskAmount(BigDecimal taskAmount) {
		this.taskAmount = taskAmount;
	}

	public BigDecimal getAmount_pre() {
		return amount_pre;
	}

	public void setAmount_pre(BigDecimal amount_pre) {
		this.amount_pre = amount_pre;
	}

	public int getTimes_pre() {
		return times_pre;
	}

	public void setTimes_pre(int times_pre) {
		this.times_pre = times_pre;
	}



	public BigDecimal getAmount_compare() {
		return amount_compare;
	}

	public void setAmount_compare(BigDecimal amount_compare) {
		this.amount_compare = amount_compare;
	}

	public int getTimes_compare() {
		return times_compare;
	}

	public void setTimes_compare(int times_compare) {
		this.times_compare = times_compare;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
