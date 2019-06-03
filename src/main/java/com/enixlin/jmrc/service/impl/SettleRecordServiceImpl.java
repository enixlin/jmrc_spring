package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.UnitPerformance;
import com.enixlin.jmrc.mapper.SettleRecordMapper;
import com.enixlin.jmrc.service.SettleRecordService;
@Service
public class SettleRecordServiceImpl extends BaseServiceImpl<SettleRecord> implements SettleRecordService {

	@Autowired SettleRecordMapper settleRecordMapper;
	 
	@Override
	public SettleRecord add(SettleRecord reocrd) {
		settleRecordMapper.insert(reocrd);
		return reocrd;
	}

	@Override
	public ArrayList<UnitPerformance> getBranchPerformance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IndexPerformance> getAllBusyTypeProformance(String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<String> products= new  ArrayList<String>();
		//products.add("汇入汇款");
		products.add("汇出汇款");
		products.add("出口信用证");
		
		return settleRecordMapper.getAllBusyTypeProformance(start,end,products);
	}

	@Override
	public ArrayList<UnitPerformance> getAllUnitPerformance(String start, String end) {
		// TODO Auto-generated method stub
		ArrayList<String> products= new  ArrayList<String>();
		//products.add("汇入汇款");
		products.add("汇出汇款");
		products.add("出口信用证");
		return settleRecordMapper.getAllUnitPerformance(start,end);
	}
}
