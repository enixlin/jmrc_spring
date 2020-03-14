/**
 * 
 */
package com.enixlin.jmrc.entity;

import java.util.ArrayList;

import org.apache.poi.hpsf.Decimal;

/**
 * 客户实体类 <br>
 * 这个客户实体类的主要用途是用于客户风险分析。<br>
 * 客户风险分析分为两个维度:<br>
 * 一个是贸易融资客户,<br>
 * 一个是结算客户<br>
 * 
 * @author linzhenhuan
 *
 */
public class Client {
	private String id; // 客户号
	private String name; // 客户名称
	private String type; // 客户类型:生产型1 贸易型2
	private Decimal asset; // 资产
	private Decimal debt; // 负债额度
	private Decimal deposit; // 存款
	private Decimal loan; // 贷款总额
	private Decimal TFAmount; // 贸易融资总额
	private Decimal income; // 收入额
	private Decimal pledge; // 担保额度
	private Decimal credit; // 授信额度
	private Decimal settlementCurrent; // 当期结算量
	private Decimal settlementPre; // 上期结算量
	private ArrayList loanDealer;// 融资业务交易对手
	private ArrayList settlementDealer;// 结算业务交易对手

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Decimal getAsset() {
		return asset;
	}

	public void setAsset(Decimal asset) {
		this.asset = asset;
	}

	public Decimal getDebt() {
		return debt;
	}

	public void setDebt(Decimal debt) {
		this.debt = debt;
	}

	public Decimal getDeposit() {
		return deposit;
	}

	public void setDeposit(Decimal deposit) {
		this.deposit = deposit;
	}

	public Decimal getLoan() {
		return loan;
	}

	public void setLoan(Decimal loan) {
		this.loan = loan;
	}

	public Decimal getTFAmount() {
		return TFAmount;
	}

	public void setTFAmount(Decimal tFAmount) {
		TFAmount = tFAmount;
	}

	public Decimal getIncome() {
		return income;
	}

	public void setIncome(Decimal income) {
		this.income = income;
	}

	public Decimal getPledge() {
		return pledge;
	}

	public void setPledge(Decimal pledge) {
		this.pledge = pledge;
	}

	public Decimal getCredit() {
		return credit;
	}

	public void setCredit(Decimal credit) {
		this.credit = credit;
	}

	public Decimal getSettlementCurrent() {
		return settlementCurrent;
	}

	public void setSettlementCurrent(Decimal settlementCurrent) {
		this.settlementCurrent = settlementCurrent;
	}

	public Decimal getSettlementPre() {
		return settlementPre;
	}

	public void setSettlementPre(Decimal settlementPre) {
		this.settlementPre = settlementPre;
	}

	public ArrayList getLoanDealer() {
		return loanDealer;
	}

	public void setLoanDealer(ArrayList loanDealer) {
		this.loanDealer = loanDealer;
	}

	public ArrayList getSettlementDealer() {
		return settlementDealer;
	}

	public void setSettlementDealer(ArrayList settlementDealer) {
		this.settlementDealer = settlementDealer;
	}

}
