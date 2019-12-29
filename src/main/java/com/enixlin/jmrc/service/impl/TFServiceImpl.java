/**
 * 
 */
package com.enixlin.jmrc.service.impl;

import com.enixlin.jmrc.mapper.TFMapper;
import com.enixlin.jmrc.service.TFService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linzhenhuan
 *
 */
@Service
public class TFServiceImpl implements TFService {
    @Autowired TFMapper tfm;

    @Override
    public int getRecordCount() {
        return tfm.getRecordCount();
    }

	@Override
	public String getLastReportDate() {
		// TODO Auto-generated method stub
		return tfm.getLastReportDate();
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTFInt(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getOrderInt(String date) {
		// TODO Auto-generated method stub
		return tfm.getOrderInt(date);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getRTInt(String date) {
		// TODO Auto-generated method stub
		return tfm.getRTInt(date);
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTFBalance(String date) {
		// TODO Auto-generated method stub
		
		ArrayList<LinkedHashMap<String, Object>> balance=new ArrayList<LinkedHashMap<String,Object>>();
		
		
		//添加科目
		ArrayList<String> subjects=new ArrayList<String>();
		ArrayList<String> special=new ArrayList<String>();
		
		subjects.add("13040301");
		special.add("0452");
		special.add("0749-“退税贷”出口退税应收款融资");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","出口退税");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("13070301");
		special.add("");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","打包贷款");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("13070101");
		special.add("");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","进口押汇");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("13070201");
		special.add("");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","出口发票融资");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("13079901");
		special.add("");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","出口信保融资");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("13040304");
		special.add("P08300201700509");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","外汇流贷");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("0105");
		special.add("");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","信用证");
		
		subjects.removeAll(subjects);
		special.removeAll(special);
		subjects.add("13030101");
		subjects.add("13040301");
		special.add("P08300201700512");
		balance.addAll(tfm.getTFBalance(date, subjects, special));
		balance.get(balance.size()-1).put("name","订单融资");
		
		
		
		
		return balance;
	}

	@Override
	public ArrayList<LinkedHashMap<String, Object>> getClientTFBalance(String date, String type) {
		// TODO Auto-generated method stub
		
		//添加科目
		ArrayList<String> subjects=new ArrayList<String>();
		ArrayList<String> special=new ArrayList<String>();
		
		if(type.equals("订单融资")) {
			subjects.removeAll(subjects);
			special.removeAll(special);
			subjects.add("13030101");
			subjects.add("13040301");
			special.add("P08300201700512");
			tfm.getClientTFBalance(date, subjects, special);
		
		}
		return null;
	}

	
	
	
}
