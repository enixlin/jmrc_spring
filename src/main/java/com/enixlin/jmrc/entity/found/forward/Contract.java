package com.enixlin.jmrc.entity.found.forward;
/**
 * 合作远期结售汇三方协议实体
 * @author linzhenhuan
 *
 */
public class Contract {

	private int id;
	private  String contractId;
	private int clientId;
	private int bankId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
}
