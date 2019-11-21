/**
 * 
 */
package com.enixlin.jmrc.service.impl;

import com.enixlin.jmrc.entity.SettleRecord;
import com.google.gson.JsonArray;

/**
 * @author linzhenhuan
 *
 */
public interface UpdateService {

	
	public String getLastUpdateDate(String type);
	
	
	
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param ja
	 *void
	 * 创建时间：2019年10月29日
	 * @return 
	 */
	public int addTF(JsonArray ja);
	
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param ja
	 *void
	 * 创建时间：2019年11月12日
	 */
	public int addSubjects(JsonArray ja);
	
	
	
	
	
	
	
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 *void
	 * 创建时间：2019年9月3日
	 */
	public void fixedSettleRecord();
	
	
	public void updatelog(String datatime, String type);



	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param sr
	 *void
	 * 创建时间：2019年11月21日
	 */
	public void addSettle(SettleRecord sr);
}
