package com.enixlin.jmrc.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.service.SettleRecordService;
import com.enixlin.jmrc.smartbi.ODS;
import com.google.gson.JsonArray;

@RestController
@RequestMapping("settlerecord")
public class SettleRecordController {
	@Autowired
	SettleRecordService srs;

	@RequestMapping("/batchInsert")
	public void add() {
		String startDayNum = "2019-01-01";
		String StarDayChn = "2019年1月1日";
		String endDayNum = "2019-05-20";
		String endDayChn = "2019年5月20日";
		String ExportNum = "20000";
		ODS ods = new ODS();
		JsonArray ja = ods.getAllSettleRecord(startDayNum, StarDayChn, endDayNum, endDayChn, ExportNum);

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
			String rate=ja.get(i).getAsJsonArray().get(27).getAsString().replace(",", "");
			if(rate.equals("")) {
				sr.setUsdRate(new BigDecimal("0.15"));
			}else {
				sr.setUsdRate(new BigDecimal(rate));
			}
			//插入记录
			srs.add(sr);

		}

		System.out.println(" 所有的记录插入完成");
	}

}