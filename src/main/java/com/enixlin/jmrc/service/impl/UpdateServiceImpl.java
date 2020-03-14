/**
 * 
 */
package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.mapper.SettleRecordMapper;
import com.enixlin.jmrc.mapper.SubjectMapper;
import com.enixlin.jmrc.mapper.TFMapper;
import com.enixlin.jmrc.mapper.UpdateMapper;
import com.enixlin.jmrc.service.UpdateService;
import com.google.gson.JsonArray;

/**
 * @author linzhenhuan
 *
 */
@Service
public class UpdateServiceImpl implements UpdateService {

	@Autowired
	UpdateMapper um;
	@Autowired
	SettleRecordMapper sm;
	@Autowired
	SubjectMapper subjectm;
	
	@Autowired 
	TFMapper tfm;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.enixlin.jmrc.service.SettleRecordService#fixedSettleRecord()
	 */
	@Override
	public void fixedSettleRecord() {
		// TODO Auto-generated method stub
		um.fixedSettleRecord_input();
		System.out.println("更正跨境人民币汇入");
		um.fixedSettleRecord_output();
		System.out.println("更正跨境人民币汇出");
		um.fixedSettleRecord_delete_1();
		um.fixedSettleRecord_delete_2();
		System.out.println("删除多余跨境人民币记录");

	}

	@Override
	public String getLastUpdateDate(String type) {
		// TODO Auto-generated method stub
		// um.deleteUpdatelelog(type);
		return um.getLastUpdateDate(type);

	}

	@Override
	public void addSettle(SettleRecord record) {
		// um.addSettle(reocrd);
		um.insert(record);

	}

	@Override
	public int addTF(JsonArray ja) {
		// TODO Auto-generated method stub

		
		return um.addTF(ja);

	}

	@Override
	public int addSubjects(JsonArray ja) {

		// um.truncateSubjectBalance();
		ArrayList<LinkedHashMap<String, Object>> record = new ArrayList<LinkedHashMap<String, Object>>();

		// 过滤数据中的逗号

		for (int n = 0; n < ja.size(); n++) {

			LinkedHashMap<String, Object> item = new LinkedHashMap<>();
			item.put("平台日期", ja.get(n).getAsJsonArray().get(0).getAsString());
			item.put("总行机构号", ja.get(n).getAsJsonArray().get(1).getAsString());
			item.put("总行机构名称", ja.get(n).getAsJsonArray().get(2).getAsString());
			item.put("支行机构号", ja.get(n).getAsJsonArray().get(3).getAsString());
			item.put("支行机构名称", ja.get(n).getAsJsonArray().get(4).getAsString());
			item.put("网点机构号", ja.get(n).getAsJsonArray().get(5).getAsString());
			item.put("网点名称", ja.get(n).getAsJsonArray().get(6).getAsString());
			item.put("总帐科目", ja.get(n).getAsJsonArray().get(7).getAsString());
			item.put("货币代码", ja.get(n).getAsJsonArray().get(8).getAsString());
			item.put("日期初借方", ja.get(n).getAsJsonArray().get(9).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(9).getAsString().replace(",", ""));
			item.put("日期初贷方", ja.get(n).getAsJsonArray().get(10).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(10).getAsString().replace(",", ""));
			item.put("日借方发生额", ja.get(n).getAsJsonArray().get(11).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(11).getAsString().replace(",", ""));
			item.put("日贷方发生额", ja.get(n).getAsJsonArray().get(12).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(12).getAsString().replace(",", ""));
			item.put("月期初借方", ja.get(n).getAsJsonArray().get(13).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(13).getAsString().replace(",", ""));
			item.put("月期初贷方", ja.get(n).getAsJsonArray().get(14).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(14).getAsString().replace(",", ""));
			item.put("月借方发生额", ja.get(n).getAsJsonArray().get(15).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(15).getAsString().replace(",", ""));
			item.put("月贷方发生额", ja.get(n).getAsJsonArray().get(16).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(16).getAsString().replace(",", ""));
			item.put("月借方余额累计数", ja.get(n).getAsJsonArray().get(17).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(17).getAsString().replace(",", ""));
			item.put("月贷方余额累计数", ja.get(n).getAsJsonArray().get(18).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(18).getAsString().replace(",", ""));
			item.put("月经过天数", ja.get(n).getAsJsonArray().get(19).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(19).getAsString().replace(",", ""));
			item.put("季期初借方", ja.get(n).getAsJsonArray().get(20).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(20).getAsString().replace(",", ""));
			item.put("季期初贷方", ja.get(n).getAsJsonArray().get(21).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(21).getAsString().replace(",", ""));
			item.put("季借方发生额", ja.get(n).getAsJsonArray().get(22).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(22).getAsString().replace(",", ""));
			item.put("季贷方发生额", ja.get(n).getAsJsonArray().get(23).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(23).getAsString().replace(",", ""));
			item.put("季借方余额累计数", ja.get(n).getAsJsonArray().get(24).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(24).getAsString().replace(",", ""));
			item.put("季贷方余额累计数", ja.get(n).getAsJsonArray().get(25).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(25).getAsString().replace(",", ""));
			item.put("季经过天数", ja.get(n).getAsJsonArray().get(26).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(26).getAsString().replace(",", ""));
			item.put("年期初借方", ja.get(n).getAsJsonArray().get(27).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(27).getAsString().replace(",", ""));
			item.put("年期初贷方", ja.get(n).getAsJsonArray().get(28).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(28).getAsString().replace(",", ""));
			item.put("年借方发生额", ja.get(n).getAsJsonArray().get(29).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(29).getAsString().replace(",", ""));
			item.put("年贷方发生额", ja.get(n).getAsJsonArray().get(30).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(30).getAsString().replace(",", ""));
			item.put("年借方余额累计数", ja.get(n).getAsJsonArray().get(31).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(31).getAsString().replace(",", ""));
			item.put("年贷方余额累计数", ja.get(n).getAsJsonArray().get(32).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(32).getAsString().replace(",", ""));
			item.put("年经过天数", ja.get(n).getAsJsonArray().get(33).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(33).getAsString().replace(",", ""));
			item.put("期末借方", ja.get(n).getAsJsonArray().get(34).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(34).getAsString().replace(",", ""));
			item.put("期末贷方", ja.get(n).getAsJsonArray().get(35).getAsString().replace(",", "").equals("") ? 0
					: ja.get(n).getAsJsonArray().get(35).getAsString().replace(",", ""));
			record.add(item);
		}

		return um.addSubjectBalance(record);

	}

	@Override
	public void updatelog(String datatime, String type) {
		// 将表中原业的该类的更新时间删除
		um.deleteUpdatelelog(type);
		um.updatelog(datatime, type);

	}

	@Override
	public void deleteSettleRecord(String start, String end) {

		um.deleteSettleRecord(start, end);

	}

	@Override
	public void deleteSubjectRecord(String start, String end) {

		um.deleteSubjectRecord(start, end);

	}

	@Override
	public void deleteTFRecord(String start, String end) {
		um.deleteTFRecord(start, end);
	}

	//根据不同的类型返回不同的更新日期
	@Override
	public String getLastBusyDate(String type) {
		// TODO Auto-generated method stub
		if (type.equals("settle")) {
			return sm.getLastBusyDate();
		}
		if (type.equals("tf")) {
			return tfm.getLastReportDate();
		}
		if (type.equals("subject")) {
			return subjectm.getLastReportDate();
		}
		return null;
	}

}
