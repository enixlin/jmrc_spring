package com.enixlin.jmrc.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.service.ExchangeService;

@RestController
@RequestMapping("exchange")
public class ExchangeController {

	@Autowired
	ExchangeService es;

	/**
	 * 取得结售汇业务的报表类型
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getExchangeReportType")
	public ArrayList<LinkedHashMap<String, Object>> getExchangeReportType(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		es.getExchangeReportType();
		return null;

	}

	/**
	 * 取得指定时间段的结售汇业务总量
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getTotalExchangePerformance")
	public ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return es.getTotalExchangePerformance(start,end);
	}
	
	
	/**
	 * 取得指定时间段的结售汇业务产品分细统计
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getTypeTotal")
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotal(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return es.getTypeTotal(start,end);
	}
	
	
	/**
	 * 取得指定时间段的结售汇业务产品分月分细统计
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getTypeTotalMonth")
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotalMonth(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		return es.getTypeTotalMonth(start,end);
	}
//	getProductMonthPerformance
	/**
	 * 取得指定时间段的单项结售汇产品分月分细统计
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getProductMonthPerformance")
	public ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String product = req.getParameter("product_name");
		
		return es.getProductMonthPerformance(product,start,end);
	}
//	getProductClientDetail
	/**
	 * 取得指定时间段的单项结售汇客户业务统计
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getProductClientDetail")
	public ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String product = req.getParameter("product_name");
		
		return es.getProductClientDetail(product,start,end);
	}
	
	
//	getUnitDetail
	/**
	 * 取得指定时间段的所有经营单位结售汇业务量统计
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getUnitDetail")
	public ArrayList<LinkedHashMap<String, Object>> getUnitDetail(HttpServletRequest req,
			HttpServletResponse res) {
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		
		return es.getUnitDetail(start,end);
	}
}
