package com.enixlin.jmrc.entity.found.forward;
/**
 * 客户远期结售汇委托下单实体类
 * @author linzhenhuan
 *
 */

import java.text.DecimalFormat;
import java.util.Date;

public class Order {

	private int id;
	private int operator;
	private String code;
	private int askCurrencyId;
	private int offerCurrncyId;
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	private int contractId;
	private Date dealDate;
	private Date selectDateStart;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getAskCurrencyId() {
		return askCurrencyId;
	}
	public void setAskCurrencyId(int askCurrencyId) {
		this.askCurrencyId = askCurrencyId;
	}
	public int getOfferCurrncyId() {
		return offerCurrncyId;
	}
	public void setOfferCurrncyId(int offerCurrncyId) {
		this.offerCurrncyId = offerCurrncyId;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public Date getSelectDateStart() {
		return selectDateStart;
	}
	public void setSelectDateStart(Date selectDateStart) {
		this.selectDateStart = selectDateStart;
	}
	public Date getSelectDateEnd() {
		return selectDateEnd;
	}
	public void setSelectDateEnd(Date selectDateEnd) {
		this.selectDateEnd = selectDateEnd;
	}
	public DecimalFormat getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(DecimalFormat dealAmount) {
		this.dealAmount = dealAmount;
	}
	public float getDealRateClient() {
		return dealRateClient;
	}
	public void setDealRateClient(float dealRateClient) {
		this.dealRateClient = dealRateClient;
	}
	public float getDealRateBank() {
		return dealRateBank;
	}
	public void setDealRateBank(float dealRateBank) {
		this.dealRateBank = dealRateBank;
	}
	public String getDepositMark() {
		return depositMark;
	}
	public void setDepositMark(String depositMark) {
		this.depositMark = depositMark;
	}
	private Date selectDateEnd;
	private DecimalFormat dealAmount;
	private float dealRateClient;
	private float dealRateBank;
	private String depositMark;
	
}
