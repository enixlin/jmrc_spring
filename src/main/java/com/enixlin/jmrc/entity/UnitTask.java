/**
 * 
 */
package com.enixlin.jmrc.entity;

import java.math.BigDecimal;

/**
 * 经营单位经营任务实体类
 * @author linzhenhuan
 *
 */
public class UnitTask {
private String code;
private String UnitName;
private String taskName;
private BigDecimal task;
	private String expiry;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public BigDecimal getTask() {
		return task;
	}

	public void setTask(BigDecimal task) {
		this.task = task;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	

}
