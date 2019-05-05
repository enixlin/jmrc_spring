package com.enixlin.jmrc.entity.found.forward;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 客户交割实体类
 * @author linzhenhuan
 *
 */
public class Complete {
	
	private int id;
	private int orderId;
	private DecimalFormat amount;
	private Date dealDate;
	private int operator;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public DecimalFormat getAmount() {
		return amount;
	}
	public void setAmount(DecimalFormat amount) {
		this.amount = amount;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	

}
