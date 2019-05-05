package com.enixlin.jmrc.entity.found.forward;
/**
 * 客户信息实体类
 * @author linzhenhuan
 *
 */

import java.text.DecimalFormat;
import java.util.Date;

public class Client {
private int id;
private String name;
private String code;
private DecimalFormat quota;
private Date expiry;
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
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public DecimalFormat getQuota() {
	return quota;
}
public void setQuota(DecimalFormat quota) {
	this.quota = quota;
}
public Date getExpiry() {
	return expiry;
}
public void setExpiry(Date expiry) {
	this.expiry = expiry;
} 



}
