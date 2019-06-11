package com.enixlin.jmrc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.UnitPerformance;
import com.enixlin.jmrc.service.SettleRecordService;
import com.enixlin.jmrc.smartbi.ODS;
import com.google.gson.JsonArray;

@RestController
@RequestMapping("settlerecord")
public class SettleRecordController {
	@Autowired
	SettleRecordService srs;

	/**
	 * 从自助分析抓取数据，并写入本地数据库
	 * 
	 * @param startDayNum
	 * @param startDayChn
	 * @param endDayNum
	 * @param endDayChn
	 * @param ExportNum
	 */
	@RequestMapping("/batchInsert")
	public void add(HttpServletRequest req, HttpServletResponse res) {

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String getMax = req.getParameter("getMax");
		String StartDayChn = changeChineseDateFormat(start);
		String endDayChn = changeChineseDateFormat(end);
		String startDayNum = changeLineDateFormat(start);
		String endDayNum = changeLineDateFormat(end);
		ODS ods = new ODS();

		JsonArray ja = ods.getAllSettleRecord(startDayNum, StartDayChn, endDayNum, endDayChn, getMax);

		for (int i = 0; i < ja.size(); i++) {
			SettleRecord sr = new SettleRecord();
			sr.setDataDate(ja.get(i).getAsJsonArray().get(0).getAsString());
			sr.setCustNumber(ja.get(i).getAsJsonArray().get(1).getAsString());
			sr.setCustName(ja.get(i).getAsJsonArray().get(2).getAsString());
			sr.setCustType(ja.get(i).getAsJsonArray().get(3).getAsString());
			sr.setBusyNumber(ja.get(i).getAsJsonArray().get(4).getAsString());
			sr.setProductName(ja.get(i).getAsJsonArray().get(6).getAsString());
			sr.setBusyType(ja.get(i).getAsJsonArray().get(5).getAsString());
			sr.setBusyCurrency(ja.get(i).getAsJsonArray().get(7).getAsString());
			// 字符串中有逗号，所以要先将符号进行替换
			sr.setBusyAmount(new BigDecimal(ja.get(i).getAsJsonArray().get(8).getAsString().replace(",", "")));
			sr.setBusyDate(ja.get(i).getAsJsonArray().get(9).getAsString());
			sr.setForeignCountry(ja.get(i).getAsJsonArray().get(10).getAsString());
			sr.setForeignBankCode(ja.get(i).getAsJsonArray().get(11).getAsString());
			sr.setForeignBankName(ja.get(i).getAsJsonArray().get(12).getAsString());
			sr.setPayerAccount(ja.get(i).getAsJsonArray().get(13).getAsString());
			sr.setPayerName(ja.get(i).getAsJsonArray().get(14).getAsString());
			sr.setReceiveAccount(ja.get(i).getAsJsonArray().get(15).getAsString());
			sr.setReceiveName(ja.get(i).getAsJsonArray().get(16).getAsString());
			sr.setBranchCode(ja.get(i).getAsJsonArray().get(17).getAsString());
			sr.setBranchName(ja.get(i).getAsJsonArray().get(18).getAsString());
			sr.setSubBranchCode(ja.get(i).getAsJsonArray().get(19).getAsString());
			sr.setSubBranchName(ja.get(i).getAsJsonArray().get(20).getAsString());
			sr.setBelongBranchCode(ja.get(i).getAsJsonArray().get(21).getAsString());
			sr.setBelongBranchName(ja.get(i).getAsJsonArray().get(22).getAsString());
			sr.setBelongSubBranchCode(ja.get(i).getAsJsonArray().get(23).getAsString());
			sr.setBelongSubBranchName(ja.get(i).getAsJsonArray().get(24).getAsString());
			sr.setOperator(ja.get(i).getAsJsonArray().get(25).getAsString());
			sr.setConfirmer(ja.get(i).getAsJsonArray().get(26).getAsString());
			// 字符串中有逗号，所以要先将符号进行替换
			String rate = ja.get(i).getAsJsonArray().get(27).getAsString().replace(",", "");
			String currency = ja.get(i).getAsJsonArray().get(7).getAsString();
			if (rate.equals("")  ) {
				sr.setUsdRate(new BigDecimal("0.0"));
			} else {
				sr.setUsdRate(new BigDecimal(rate));
			}
			// 插入记录
			srs.add(sr);

		}

		System.out.println(" 所有的记录插入完成");
	}

	@RequestMapping("/getAllBusyTypeProformance")
	public ArrayList<IndexPerformance> getAllBusyTypeProformance(HttpServletRequest req, HttpServletResponse res) {

		String start = req.getParameter("start");
		String end = req.getParameter("end");
//		if(start.equals(null) || start.equals("")) {
//			start="20190101";
//		}
//		if(end.equals(null) || end.equals("")) {
//			end="20190501";
//		}
//		String start="20190101";
//		String end="2019031";
	
			

		return srs.getAllBusyTypeProformance(start, end);

	}

	/**
	 * 将日期格式转换为中文格式
	 * 
	 * @param day //格式：20190930
	 * @return
	 */
	public String changeChineseDateFormat(String day) {
		return day.substring(0, 4) + "年" + day.substring(4, 6) + "月" + day.substring(6, 8) + "日";

	}

	/**
	 * 将日期格式转换为横线格式
	 * 
	 * @param day //格式：20190930
	 * @return
	 */
	public String changeLineDateFormat(String day) {
		return day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6, 8);

	}
	
	/**
	 * 取得所有经营单位的实绩
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getAllUnitPerformance")
	public ArrayList<UnitPerformance>  getAllUnitPerformance(HttpServletRequest req,HttpServletResponse res){
		String start=req.getParameter("start");
		String end =req.getParameter("end");
		return srs.getAllUnitPerformance(start,end);
		
	}
	
	
	/**
	 * 取得每月的结算量
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getMonthPerformance")
	public List<IndexPerformance> getMonthPerformance(HttpServletRequest req,HttpServletResponse res){
		String start=req.getParameter("start");
		String end=req.getParameter("end");
		return srs.getMonthPerformance(start,end);
		
	}

}
