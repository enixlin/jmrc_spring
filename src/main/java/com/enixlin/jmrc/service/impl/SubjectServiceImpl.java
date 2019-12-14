package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.bcel.generic.Select;
import org.apache.ibatis.jdbc.SQL;
import org.apache.xpath.operations.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import groovy.time.BaseDuration.From;

import com.enixlin.jmrc.mapper.SubjectMapper;
import com.enixlin.jmrc.service.SubjectService;
import com.enixlin.jmrc.util.Utils;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	SubjectMapper sm;
	@Autowired
	SubjectService ss;
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

		// 取得当天的美元汇率
		String rate = ss.getUsdRateFromSettleRecord("cny", date);
		Float rate_f = Float.parseFloat(rate);

		ArrayList<LinkedHashMap<String, Object>> hm = new ArrayList<LinkedHashMap<String, Object>>();
		// 取得汇兑收益
		String subject = "3301";
		String currency = "rmb";
		ArrayList<LinkedHashMap<String, Object>> arr_exchange = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得同业业务收益
		subject = "6012";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_bank = sm.getIncomeSubjectByCurrency(date, subject, currency);
				// 取得存款利息支出
				subject = "6411";
				currency = "usx";
				ArrayList<LinkedHashMap<String, Object>> arr_deposit_int = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得同业业务支出
		subject = "6412";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_bank_cost = sm.getIncomeSubjectByCurrency(date, subject, currency);
		// 取得外汇业务手续费支出
		subject = "64210401";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_cost = sm.getIncomeSubjectByCurrency(date, subject,
				currency);
		subject = "64210401";
		currency = "cny";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_cost_c = sm.getIncomeSubjectByCurrency(date, subject,
				currency);
		//外汇业务手续费支出=外币原币支出+人民币支出
		Float usd_c = Float.parseFloat(arr_charge_cost.get(0).get("debit_end_c").toString());
		Float cny_c = Float.parseFloat(arr_charge_cost_c.get(0).get("debit_end_c").toString());
		arr_charge_cost.get(0).replace("debit_end_c", usd_c + cny_c*rate_f);
		Float usd_p = Float.parseFloat(arr_charge_cost.get(0).get("debit_end_p").toString());
		Float cny_p = Float.parseFloat(arr_charge_cost_c.get(0).get("debit_end_p").toString());
		arr_charge_cost.get(0).replace("debit_end_c", usd_p + cny_p*rate_f);
		// 取得外汇业务手续费收入
		subject = "60210301";
		currency = "rmb";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_earn = sm.getIncomeSubjectByCurrency(date, subject,
				currency);

		hm.addAll(arr_exchange);
		hm.addAll(arr_deposit_int);
		hm.addAll(arr_bank);
		hm.addAll(arr_bank_cost);
		hm.addAll(arr_charge_cost);
		// hm.addAll(arr_charge_cost_c);
		hm.addAll(arr_charge_earn);


		for (int n = 0, len = hm.size(); n < len; n++) {
			String cur = hm.get(n).get("currency").toString();
			Float credit_end_c = Float.parseFloat(hm.get(n).get("credit_end_c").toString());
			Float debit_end_c = Float.parseFloat(hm.get(n).get("debit_end_c").toString());
			if (cur.equals("USX")) {
				hm.get(n).replace("credit_end_c", credit_end_c / rate_f);
				hm.get(n).replace("debit_end_c", debit_end_c / rate_f);
			}
			Float credit_end_p = Float.parseFloat(hm.get(n).get("credit_end_p").toString());
			Float debit_end_p = Float.parseFloat(hm.get(n).get("debit_end_p").toString());
			if (cur.equals("USX")) {
				hm.get(n).replace("credit_end_p", credit_end_p / rate_f);
				hm.get(n).replace("debit_end_p", debit_end_p / rate_f);
			}
			hm.get(n).put("credit_compare",credit_end_c-credit_end_p );
			hm.get(n).put("debit_compare",debit_end_c-debit_end_p );

			hm.get(n).put("credit_compare_precent",(credit_end_c-credit_end_p)/credit_end_p );
			hm.get(n).put("debit_compare_precent",(debit_end_c-debit_end_p)/debit_end_p );
		}
		
	


		return hm;

	}

	@Override
	public String getUsdRateFromSettleRecord(String currency, String date) {

		return sm.getUsdRateFromSettleRecord(currency, date);

	}

}