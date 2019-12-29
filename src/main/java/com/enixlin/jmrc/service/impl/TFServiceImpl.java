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
		
		
		
		
		
		return balance;
	}

	
	
	
}
