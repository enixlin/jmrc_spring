package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.entity.Performance;
import com.enixlin.jmrc.entity.SettleRecord;
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
	public ArrayList<Performance> getBranchPerformance() {
		// TODO Auto-generated method stub
		return null;
	}
}
