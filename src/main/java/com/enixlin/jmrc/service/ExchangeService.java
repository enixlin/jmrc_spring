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

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param product
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年11月22日
	 */
	public ArrayList<LinkedHashMap<String, Object>> getUnitDetail(
			 String start, String end);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年11月22日
	 */
	ArrayList<String> getExchangeProduct();

	public ArrayList<LinkedHashMap<String, Object>> getUnitMonth(String unit, String start, String end);

	public ArrayList<LinkedHashMap<String, Object>> getUnitProduct(String unit, String start, String end);

	public ArrayList<LinkedHashMap<String, Object>> getUnitClientList(String unit, String start, String end);

	public ArrayList<LinkedHashMap<String, Object>> getClientDetail(String start, String end);

	
	
}
