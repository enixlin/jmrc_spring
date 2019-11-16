package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface ExchangeService {

	public void getExchangeReportType() ;

	public ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(String start, String end);

	public ArrayList<LinkedHashMap<String, Object>> getTypeTotal(String start, String end);
	
	
}
