package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface ExchangeService {

	public void getExchangeReportType() ;

	public ArrayList<LinkedHashMap<String, Object>> getTotalExchangePerformance(String start, String end);

	public ArrayList<LinkedHashMap<String, Object>> getTypeTotal(String start, String end);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年11月17日
	 */
	public ArrayList<LinkedHashMap<String, Object>> getTypeTotalMonth(
			String start, String end);

	public ArrayList<LinkedHashMap<String, Object>> getProductMonthPerformance(String product, String start,
			String end);

	public ArrayList<LinkedHashMap<String, Object>> getProductClientDetail(String product, String start, String end);
	
	
}
