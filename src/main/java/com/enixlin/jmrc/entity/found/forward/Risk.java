package com.enixlin.jmrc.entity.found.forward;

import java.text.DecimalFormat;
import java.math.*;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.KeySql;

/**
 * 远期结售汇风险系数实体类
 * 
 * @author linzhenhuan
 *
 */
@Table(name="corp_forward_risk")
public class Risk {
@Id
//@KeySql(useGeneratedKeys = true)//获取数据库自增的主键
private int id;
private int expiry;//期限，单位是天数
private BigDecimal  maincurencyrisk;
private BigDecimal othercurrencyrisk;
private GregorianCalendar effectdate;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getExpiry() {
	return expiry;
}
public void setExpiry(int expiry) {
	this.expiry = expiry;
}
public BigDecimal getMaincurencyrisk() {
	return maincurencyrisk;
}
public void setMaincurencyrisk(BigDecimal maincurencyrisk) {
	this.maincurencyrisk = maincurencyrisk;
}
public BigDecimal getOthercurrencyrisk() {
	return othercurrencyrisk;
}
public void setOthercurrencyrisk(BigDecimal othercurrencyrisk) {
	this.othercurrencyrisk = othercurrencyrisk;
}
public GregorianCalendar getEffectdate() {
	return effectdate;
}
public void setEffectdate(GregorianCalendar effectdate) {
	this.effectdate = effectdate;
}





}
