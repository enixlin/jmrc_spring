package com.enixlin.jmrc.service;

import java.util.ArrayList;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.UnitPerformance;

public interface SettleRecordService extends BaseService<SettleRecord> {

	public ArrayList<UnitPerformance> getBranchPerformance();


	public ArrayList<IndexPerformance> getAllBusyTypeProformance(String start, String end);


	public ArrayList<UnitPerformance> getAllUnitPerformance(String start, String end);
	
	
}
