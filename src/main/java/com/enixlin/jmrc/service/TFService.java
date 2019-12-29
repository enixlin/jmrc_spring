/**
 * 
 */
package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author linzhenhuan
 *
 */

public interface TFService {

	int getRecordCount();
	String getLastReportDate();
	
	ArrayList<LinkedHashMap<String, Object>> getTFInt(String date);
	ArrayList<LinkedHashMap<String, Object>> getOrderInt(String date);
	ArrayList<LinkedHashMap<String, Object>> getRTInt(String date);
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param date 
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年12月27日
	 */
	ArrayList<LinkedHashMap<String, Object>> getTFBalance(String date);
	ArrayList<LinkedHashMap<String, Object>> getClientTFBalance(String date, String type);
	
	
	

	
}
