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
import com.enixlin.jmrc.service.TFService;
import com.enixlin.jmrc.util.Utils;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	SubjectMapper sm;
	@Autowired
	SubjectService ss;

	@Autowired
	TFService tfs;

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
		this.date = date;////
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

		// 取得贸易融资收益
		subject = "601107";
		currency = "rmb";
		ArrayList<LinkedHashMap<String, Object>> arr_tf1 = sm.getIncomeSubjectByCurrency(date, subject, currency);

		// 外币流贷收益
		subject = "60110403";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_tf2 = sm.getIncomeSubjectByCurrency(date, subject, currency);

		// 取得退税收益
		// 这个收益要从信贷中间表取得
		subject = "13040301";
		ArrayList<LinkedHashMap<String, Object>> arr_tf3 = tfs.getRTInt(date);
		Double RTInt_c = 0.0;
		Double RTInt_p = 0.0;
		if (arr_tf3.size() != 0) {
			RTInt_c = Double.parseDouble(arr_tf3.get(0).get("sumInt_c").toString());
			RTInt_p = Double.parseDouble(arr_tf3.get(0).get("sumInt_p").toString());
		}

		// 取得订单融资收益
		// 这个收益要从信贷中间表取得
		ArrayList<LinkedHashMap<String, Object>> arr_tf4 = tfs.getOrderInt(date);
		Double OrderInt_c = 0.0;
		Double OrderInt_p = 0.0;
		if (arr_tf4.size() != 0) {
			OrderInt_c = Double.parseDouble(arr_tf4.get(0).get("sumInt_c").toString());
			OrderInt_p = Double.parseDouble(arr_tf4.get(0).get("sumInt_p").toString());
		}

		// 取得存款利息支出
		subject = "6411";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_deposit_int = sm.getIncomeSubjectByCurrency(date, subject,
				currency);
		// 取得同业业务支出
		subject = "6412";
		currency = "usx";
		ArrayList<LinkedHashMap<String, Object>> arr_bank_cost = sm.getIncomeSubjectByCurrency(date, subject, currency);

		// 取得外汇业务手续费支出
		subject = "64210401";
		currency = "USX";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_cost = sm.getIncomeSubjectByCurrency(date, subject,
				currency);
		subject = "64210401";
		currency = "CNY";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_cost_c = sm.getIncomeSubjectByCurrency(date, subject,
				currency);
		// 外汇业务手续费支出=外币原币支出+人民币支出
		Double usd_c = (Double) 0.0;
		if (arr_charge_cost.size() != 0) {
			usd_c = (double) Float.parseFloat(arr_charge_cost.get(0).get("debit_end_c").toString());
		}
		Float cny_c = (float) 0.0;
		if (arr_charge_cost_c.size() != 0) {
			cny_c = Float.parseFloat(arr_charge_cost_c.get(0).get("debit_end_c").toString());

		}
		if(arr_charge_cost.size()==0){
			LinkedHashMap<String, Object> lhmc = new LinkedHashMap<>();
			lhmc.put("subject", "64210401");
			lhmc.put("currency", "USX");
			lhmc.put("credit_end_c", "0");
			lhmc.put("debit_end_c", "0");
			lhmc.put("credit_end_p", 0);
			lhmc.put("debit_end_p", 0);
			lhmc.put("credit_compare", 0);
			lhmc.put("debit_compare", 0);
			lhmc.put("credit_compare_precent", 0);
			lhmc.put("debit_compare_precent", 0);
			arr_charge_cost.add(lhmc);

		}else{
			arr_charge_cost.get(0).put("debit_end_c", usd_c + cny_c * rate_f);
		}

		Float usd_p = (float) 0.0;
		if (arr_charge_cost_c.size() != 0) {
			usd_p = Float.parseFloat(arr_charge_cost.get(0).get("debit_end_p").toString());
		}

		Float cny_p = (float) 0.0;
		if (arr_charge_cost_c.size() != 0) {
			cny_p = Float.parseFloat(arr_charge_cost_c.get(0).get("debit_end_p").toString());
		}
		

		if(arr_charge_cost_c.size()==0){
			LinkedHashMap<String, Object> lhmcc = new LinkedHashMap<>();
			lhmcc.put("subject", "64210401");
			lhmcc.put("currency", "USX");
			lhmcc.put("credit_end_c", "0");
			lhmcc.put("debit_end_c", "0");
			lhmcc.put("credit_end_p", 0);
			lhmcc.put("debit_end_p", 0);
			lhmcc.put("credit_compare", 0);
			lhmcc.put("debit_compare", 0);
			lhmcc.put("credit_compare_precent", 0);
			lhmcc.put("debit_compare_precent", 0);
			arr_charge_cost.add(lhmcc);

		}else{
			arr_charge_cost.get(0).put("debit_end_p", usd_p + cny_p * rate_f);
		}

		// 取得外汇业务手续费收入
		subject = "60210301";
 		currency = "rmb";
		ArrayList<LinkedHashMap<String, Object>> arr_charge_earn = sm.getIncomeSubjectByCurrency(date, subject,
				currency);

		hm.addAll(arr_exchange);
		hm.addAll(arr_deposit_int);
		hm.addAll(arr_tf1);
		hm.addAll(arr_tf2);
		hm.addAll(arr_bank);
		hm.addAll(arr_bank_cost);
		hm.addAll(arr_charge_cost);
		// hm.addAll(arr_charge_cost_c);
		hm.addAll(arr_charge_earn);

		LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();
		lhm.put("subject", "13040301");
		lhm.put("currency", "cny");
		lhm.put("credit_end_c", RTInt_c);
		lhm.put("debit_end_c", "0");
		lhm.put("credit_end_p", RTInt_p);
		lhm.put("debit_end_p", 0);
		lhm.put("credit_compare", RTInt_c-RTInt_p);
		lhm.put("debit_compare", 0);
		lhm.put("credit_compare_precent", 0);
		lhm.put("debit_compare_precent", 0);
		hm.add(lhm);

		LinkedHashMap<String, Object> lhmOrder = new LinkedHashMap<>();
		lhmOrder.put("subject", "13030101");
		lhmOrder.put("currency", "cny");
		lhmOrder.put("credit_end_c", OrderInt_c);
		lhmOrder.put("debit_end_c", "0");
		lhmOrder.put("credit_end_p",OrderInt_p );
		lhmOrder.put("debit_end_p", 0);
		lhmOrder.put("credit_compare", OrderInt_c-OrderInt_p);
		lhmOrder.put("debit_compare", "0");
		lhmOrder.put("credit_compare_precent", 0);
		lhmOrder.put("debit_compare_precent", 0);
		hm.add(lhmOrder);

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
			if(cur.equals("USX")){
				hm.get(n).put("credit_compare", (credit_end_c - credit_end_p)/rate_f);
				hm.get(n).put("debit_compare", (debit_end_c - debit_end_p)/rate_f);
			}else{
				hm.get(n).put("credit_compare", (credit_end_c - credit_end_p));
				hm.get(n).put("debit_compare", (debit_end_c - debit_end_p));
			}

			hm.get(n).put("credit_compare_precent", (credit_end_c - credit_end_p) / credit_end_p);
			hm.get(n).put("debit_compare_precent", (debit_end_c - debit_end_p) / debit_end_p);

			if (hm.get(n).get("subject").equals("6411")) {
				hm.get(n).put("subjectName", "存款利息支出");

			}
			if (hm.get(n).get("subject").equals("601107")) {
				hm.get(n).put("subjectName", "贸易融资利息收入");
			}
			if (hm.get(n).get("subject").equals("13040301")) {
				hm.get(n).put("subjectName", "退税贷款利息收入");
			}
			if (hm.get(n).get("subject").equals("13030101")) {
				hm.get(n).put("subjectName", "订单融资利息收入");
			}
			if (hm.get(n).get("subject").equals("60110403")) {
				hm.get(n).put("subjectName", "外币流贷利息收入");
			}

			if (hm.get(n).get("subject").equals("60210301")) {
				hm.get(n).put("subjectName", "外汇手续费收入");
			}
			if (hm.get(n).get("subject").equals("64210401")) {
				hm.get(n).put("subjectName", "外汇手续费支出");
			}
			if (hm.get(n).get("subject").equals("3301")) {
				hm.get(n).put("subjectName", "汇兑收益");
			}
			if (hm.get(n).get("subject").equals("6012")) {
				hm.get(n).put("subjectName", "同业业务收入");
			}
			if (hm.get(n).get("subject").equals("6412")) {
				hm.get(n).put("subjectName", "同业业务支出");
			}

		}

		return hm;

	}

	@Override
	public String getUsdRateFromSettleRecord(String currency, String date) {

		return sm.getUsdRateFromSettleRecord(currency, date);

	}

}