/**
 * 
 */
/**
 * @author linzhenhuan
 *
 */
module jmrc_spring {
	exports com.enixlin.jmrc;
	exports com.enixlin.jmrc.service.impl.found.forward;
	exports com.enixlin.jmrc.entity;
	exports com.enixlin.jmrc.policydocument;
	exports com.enixlin.jmrc.smartbi;
	exports com.enixlin.jmrc.schedule;
	exports com.enixlin.jmrc.service.impl;
	exports com.enixlin.jmrc.service.found.forward;
	exports com.enixlin.jmrc.controller;
	exports com.enixlin.jmrc.util;
	exports com.enixlin.jmrc.mapper;
	exports com.enixlin.jmrc.entity.found.forward;
	exports com.enixlin.jmrc.service;

	requires flex.messaging.core;
	requires java.desktop;
	requires java.sql;
	requires javax.persistence;
	requires javax.servlet;
	requires mysql.connector.java;
	requires spring.beans;
	requires spring.context;
	requires spring.tx;
}