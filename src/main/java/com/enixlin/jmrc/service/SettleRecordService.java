package com.enixlin.jmrc.service;

import java.util.ArrayList;

import com.enixlin.jmrc.entity.Performance;
import com.enixlin.jmrc.entity.SettleRecord;

public interface SettleRecordService extends BaseService<SettleRecord> {

	public ArrayList<Performance> getBranchPerformance();
	
	
}
