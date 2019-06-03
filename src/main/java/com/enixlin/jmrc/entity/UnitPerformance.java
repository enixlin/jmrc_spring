package com.enixlin.jmrc.entity;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 经营单位经营实绩实现类
 * 
 * @author linzhenhuan
 *
 */
public class UnitPerformance {

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
	private String code;
	private String name;
	private BigDecimal performance;
	



	
	
}
