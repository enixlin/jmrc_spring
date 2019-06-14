package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.List;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.SettleRange;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.UnitPerformance;

public interface SettleRecordService extends BaseService<SettleRecord> {

	public ArrayList<UnitPerformance> getBranchPerformance();

	public ArrayList<IndexPerformance> getAllBusyTypeProformance(String start,
			String end);

	public ArrayList<UnitPerformance> getAllUnitPerformance(String start,
			String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return List<IndexPerformance> 创建时间：2019年6月11日
	 */
	public List<IndexPerformance> getMonthPerformance(String start, String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return List<SettleRange> 创建时间：2019年6月13日
	 */
	public List<SettleRange> getsettleRange();

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @return
	 *List<String>
	 * 创建时间：2019年6月13日
	 */
	public List<String> getAllProduct();

}
