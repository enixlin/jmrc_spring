package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.mapper.SubjectMapper;
import com.enixlin.jmrc.service.SubjectService;
import com.enixlin.jmrc.util.Utils;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	SubjectMapper sm;
	private String date;

	public String getLastReportDate() {
		// TODO Auto-generated method stub
		return sm.getLastReportDate();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getDepositSubjects(final String date) {
		// TODO Auto-generated method stub
		final Utils utils = new Utils();
		final String date_pre = utils.compareYearLastDate(date);

		final ArrayList<String> subjects = new ArrayList<String>();

		subjects.add("2001");
		subjects.add("2014");
		subjects.add("2003");
		subjects.add("2004");
		final ArrayList<LinkedHashMap<String, Object>> arr_agent = sm.getDepositSubjectsAgent(date, subjects);
		final ArrayList<LinkedHashMap<String, Object>> arr_self = sm.getDepositSubjectsSelf(date, subjects);
		for (int j = 0; j < arr_self.size(); j++) {
			arr_self.get(j).put("range", "self");
		}
		for (int i = 0; i < arr_agent.size(); i++) {
			arr_agent.get(i).put("range", "agent");
			arr_self.add(arr_agent.get(i));
		}
		return arr_self;

	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getIncomeSubject(final String date) {
		this.date = date;
		// TODO Auto-generated method stub
		ArrayList<LinkedHashMap<String, Object>> hm = new ArrayList<LinkedHashMap<String, Object>>();
		// 取得汇兑收益
		String subject = "3301";
		String currency = "rmb";
		ArrayList<LinkedHashMap<String, Object>> arr_exchange = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得同业业务收益
		subject = "6012";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_bank = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得同业业务支出
		subject = "6412";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_bank_cost = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得外汇业务手续费支出
		subject = "64210401";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_cost = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得外汇业务手续费收入
		subject = "60210301";
		currency = "rmb";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_earn = sm.getIncomeSubjectByCurrency(date, subject, currency);

		hm.addAll(arr_exchange);
		hm.addAll(arr_bank);
		hm.addAll(arr_bank_cost);
		hm.addAll(arr_charge_cost);
		hm.addAll(arr_charge_earn);
		return hm;

	}

}