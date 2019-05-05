package com.enixlin.jmrc.entity.found.forward;
/**
 * 客户账户实体类
 * @author linzhenhuan
 *
 */
public class Account {
private int id;
private int clientId;
private int currencyId;
private String accountId;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getClientId() {
	return clientId;
}
public void setClientId(int clientId) {
	this.clientId = clientId;
}
public int getCurrencyId() {
	return currencyId;
}
public void setCurrencyId(int currencyId) {
	this.currencyId = currencyId;
}
public String getAccountId() {
	return accountId;
}
public void setAccountId(String accountId) {
	this.accountId = accountId;
}



}
