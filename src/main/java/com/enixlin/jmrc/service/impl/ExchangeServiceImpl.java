package com.enixlin.jmrc.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enixlin.jmrc.mapper.ExchangeMapper;
import com.enixlin.jmrc.service.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService{

	@Autowired ExchangeMapper em;
	@Override
	public void getExchangeReportType() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(String start, String end) {
		// TODO Auto-generated method stub
		return em.getTotalExchangePerformance(start,end);
	}

	
}
