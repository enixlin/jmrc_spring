/**
 * 
 */
package com.enixlin.jmrc.entity;

/**
 * 
 * 交易对手实体类<br>
 * 主要用于记录贸易融资客户和结算客户的交易对手的信息
 * 
 * @author linzhenhuan
 *
 */
public class Dealer {

	private String id;
	private String name;
	private String country;
	private String type;
	private String account;
	private String belongClientId;



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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBelongClientId() {
		return belongClientId;
	}

	public void setBelongClientId(String belongClientId) {
		this.belongClientId = belongClientId;
	}

}
