package com.enixlin.jmrc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.MonthPerformace;
import com.enixlin.jmrc.entity.Product;
import com.enixlin.jmrc.entity.SettleRange;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.Unit;
import com.enixlin.jmrc.entity.UnitPerformance;

public interface SettleRecordService extends BaseService<SettleRecord> {

	public ArrayList<UnitPerformance> getBranchPerformance();

	public ArrayList<IndexPerformance> getAllBusyTypeProformance(String start,
			String end);

	/**
	 * 取得所有的经营机构的国际结算业绩（分产品类别）
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<UnitPerformance>
	 * 创建时间：2019年7月31日
	 */
	public ArrayList<LinkedHashMap<String, Object>>  getAllUnitPerformance(
			String start,
			String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return List<SettleRange> 创建时间：2019年6月13日
	 */
	public List<SettleRange> getsettleRange();

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return List<String> 创建时间：2019年6月13日
	 */
	public ArrayList<String> getAllProduct();

	/**
	 * 国际结算按月统计
	 * 
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @param product 纳入国际结算量统计口径的产品（这是一个数组类型）
	 * @return List<IndexPerformance> 创建时间：2019年6月27日
	 */
	List<IndexPerformance> getMonthPerformance(String start, String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param products
	 * @return int 创建时间：2019年7月7日
	 */
	public int saveSettleRangeProduct(ArrayList<String> products);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return ArrayList<String> 创建时间：2019年7月8日
	 */
	public ArrayList<String> getAllProductFromSettleRecord();

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return ArrayList<Product> 创建时间：2019年7月8日
	 */
	public ArrayList<Product> getSettleRangeProduct();

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月23日
	 */
	public ArrayList<IndexPerformance> getAllBusySettleTypeProformance(
			String start, String end);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return ArrayList<String> 创建时间：2019年7月26日
	 */
	public ArrayList<String> getAllUnitName();

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @return ArrayList<Unit> 创建时间：2019年7月27日
	 */
	public ArrayList<Unit> getAllUnitInfo();

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月28日
	 */
	public ArrayList<IndexPerformance> getSettleTypeProformanceByDate(
			String start, String end);

	/**
	 * 
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @param product
	 * @return ArrayList<SettleRecord> 创建时间：2019年7月29日
	 */
	public ArrayList<SettleRecord> getDateRangeDetail(String start, String end,
			String product);

	/**
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @return HashMap<String,ArrayList<IndexPerformance>> 创建时间：2019年7月30日
	 */
	public HashMap<String, ArrayList<IndexPerformance>> allUnitSettlePerformance(
			String start, String end);

	/**
	 * 按用户名或机构名称，取得指定业务指标的业务量
	 * @author linzhenhuan </br>
	 *         方法说明： </br>
	 * @param start
	 * @param end
	 * @param unit
	 * @param UnitType  
	 * @param Index
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月30日
	 */
	IndexPerformance getPerformanceByUnitNameAndIndex(String start,
			String end, Unit unit, String UnitType, ArrayList<Product> products);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param start
	 * @param end
	 * @param unit
	 * @param products
	 * @param unitType
	 * @return
	 *HashMap<String,ArrayList<IndexPerformance>>
	 * 创建时间：2019年7月30日
	 */
	public ArrayList<IndexPerformance> getUnitIndexPerformance(
			String start, String end, Unit unit, ArrayList<Product> products);




	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<MonthPerformace>
	 * 创建时间：2019年8月3日
	 */
	public ArrayList<MonthPerformace> getUnitMonthPerformace(Unit unit,
			String start, String end);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<HashMap<String,Object>>
	 * 创建时间：2019年8月25日
	 */
	public ArrayList<HashMap<String, Object>> getAllClientPerformance(
			String start, String end,String clientType);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param unitType
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<MonthPerformace>
	 * 创建时间：2019年8月31日
	 */
	public ArrayList<LinkedHashMap<String, Object>> getUnitSettleMonthPerformance(Unit unit,
			String start, String end);

	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年9月2日
	 */
	public ArrayList<LinkedHashMap<String, Object>> getClientSettleMonthPerformance(
			Unit unit, String start, String end);
	
	

}
