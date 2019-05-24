package com.enixlin.jmrc.entity;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 经营单位经营实绩实现类
 * 
 * @author linzhenhuan
 *
 */
public class Performance {

	private String unitCode;
	private String unitName;
	private HashMap<String, BigDecimal> task;
	private HashMap<String, BigDecimal> performance;
	
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public HashMap<String, BigDecimal> getTask() {
		return task;
	}
	public void setTask(HashMap<String, BigDecimal> task) {
		this.task = task;
	}
	public HashMap<String, BigDecimal> getPerformance() {
		return performance;
	}
	public void setPerformance(HashMap<String, BigDecimal> performance) {
		this.performance = performance;
	}
	
	
}
