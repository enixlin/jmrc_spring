/**
 * 
 */
package com.enixlin.jmrc.entity;

/**
 * 
 * 结算量统计口径指标实体类
 * 
 * @author linzhenhuan
 *
 */
public class SettleRange {

	private int id;
	private String name;
	private boolean isRange;

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

	public boolean isRange() {
		return isRange;
	}

	public void setRange(boolean isRange) {
		this.isRange = isRange;
	}

}
