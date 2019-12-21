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
	

	
}
