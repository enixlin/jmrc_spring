package com.enixlin.jmrc.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 远期签约实体类
 * @author linzhenhuan
 *
 */
@Table(name="corp_forward_order")
public class ForwardOrder {

	private String sId;
	@Id
	private String busyId;
	private String constractId;
	private String orderId;
	private String orderId2;
	private String dealType;
	private String clientId;
	private String clientName;
	private Date dealDate;
	private Date completeStartDate;
	private Date completeEndDate;
	private Date completeDate;
	private String currency;
	private BigDecimal amount;
	private BigDecimal rateSelf;
	private BigDecimal rateCorp;
	private String corpBankName;
	private BigDecimal usdAmount;
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getBusyId() {
		return busyId;
	}
	public void setBusyId(String busyId) {
		this.busyId = busyId;
	}
	public String getConstractId() {
		return constractId;
	}
	public void setConstractId(String constractId) {
		this.constractId = constractId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId2() {
		return orderId2;
	}
	public void setOrderId2(String orderId2) {
		this.orderId2 = orderId2;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public Date getCompleteStartDate() {
		return completeStartDate;
	}
	public void setCompleteStartDate(Date completeStartDate) {
		this.completeStartDate = completeStartDate;
	}
	public Date getCompleteEndDate() {
		return completeEndDate;
	}
	public void setCompleteEndDate(Date completeEndDate) {
		this.completeEndDate = completeEndDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRateSelf() {
		return rateSelf;
	}
	public void setRateSelf(BigDecimal rateSelf) {
		this.rateSelf = rateSelf;
	}
	public BigDecimal getRateCorp() {
		return rateCorp;
	}
	public void setRateCorp(BigDecimal rateCorp) {
		this.rateCorp = rateCorp;
	}
	public String getCorpBankName() {
		return corpBankName;
	}
	public void setCorpBankName(String corpBankName) {
		this.corpBankName = corpBankName;
	}
	public BigDecimal getUsdAmount() {
		return usdAmount;
	}
	public void setUsdAmount(BigDecimal usdAmount) {
		this.usdAmount = usdAmount;
	}
	
}
