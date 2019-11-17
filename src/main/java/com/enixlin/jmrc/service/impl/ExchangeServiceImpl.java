package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.mapper.ExchangeMapper;
import com.enixlin.jmrc.service.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService {

	@Autowired
	ExchangeMapper em;

	public String compareDate(String date) {
		String s = "";
		int year = Integer.parseInt(date.substring(0, 4));
		s = String.valueOf(year - 1) + date.substring(4);
		return s;
	}

	@Override
	public void getExchangeReportType() {

	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(String start, String end) {
		// TODO Auto-generated method stub
		return em.getTotalExchangePerformance(start, end);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotal(String start, String end) {
		// TODO Auto-generated method stub
		String start_pre = this.compareDate(start);
		String end_pre = this.compareDate(end);
		ArrayList<LinkedHashMap<String, Object>> arr_current = em.getTypeTotal(start, end);
		ArrayList<LinkedHashMap<String, Object>> arr_pre = em.getTypeTotal(start_pre, end_pre);
		for (LinkedHashMap<String, Object> element_current : arr_current) {
			int existFlag = 0;
			for (LinkedHashMap<String, Object> element_pre : arr_pre) {
				String n_pre=(String) element_pre.get("product_name");
				String n_current=(String) element_current.get("product_name");
				if(n_current.equals(n_pre)) {
					long times_compare=(long)element_current.get("times")-(long)element_pre.get("times");
					float amount_compare=Float.parseFloat(element_current.get("amount").toString())-Float.parseFloat(element_pre.get("amount").toString());
					element_current.put("times_pre", element_pre.get("times"));
					element_current.put("amount_pre", element_pre.get("amount"));
					element_current.put("times_compare", times_compare);
					element_current.put("amount_compare", amount_compare);
					existFlag=1;
				}
				
			}
			if(existFlag==0) {
				element_current.put("times_pre", 0);
				element_current.put("amount_pre", 0);
				element_current.put("times_compare", element_current.get("times"));
				element_current.put("amount_compare", element_current.get("amount"));
			}
		}
		
		for (LinkedHashMap<String, Object> element_pre : arr_pre) {
			int existFlag = 0;
			for (LinkedHashMap<String, Object> element_current : arr_current) {
				String n_pre=(String) element_pre.get("product_name");
				String n_current=(String) element_current.get("product_name");
				if(n_current.equals(n_pre)) {
					existFlag=1;
				}
				
			}
			if(existFlag==0) {
				LinkedHashMap<String, Object> newHM=new LinkedHashMap<>();
				long times_compare=0-Long.parseLong((String) element_pre.get("times"));
				float amount_compare=0-Float.parseFloat((String) element_pre.get("amount"));
				newHM.put("product_name", element_pre.get("product_name"));
				newHM.put("times", 0);
				newHM.put("amount", 0);
				newHM.put("times_pre", element_pre.get("times"));
				newHM.put("amount_pre", element_pre.get("amount"));
				newHM.put("times_compare", times_compare);
				newHM.put("amount_compare", amount_compare);
				arr_current.add(newHM);
			}
		}
		
		
		
		
		return arr_current;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotalMonth(
			String start, String end) {
		// TODO Auto-generated method stub
		return em.getTypeTotalMonth(start,end);
	}

}