package com.enixlin.jmrc.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.enixlin.jmrc.entity.IndexPerformance;
import com.enixlin.jmrc.entity.MonthPerformace;
import com.enixlin.jmrc.entity.Product;
import com.enixlin.jmrc.entity.SettleRange;
import com.enixlin.jmrc.entity.SettleRecord;
import com.enixlin.jmrc.entity.Unit;
import com.enixlin.jmrc.entity.UnitPerformance;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface SettleRecordMapper extends BaseMapper<SettleRecord> {

	@Select(" "
			+ "SELECT "
			+ "sum(usd_Rate * busy_Amount)/10000 as performance ,"
			+ "count(busy_amount) as times,"
			+ "product_Name as name "
			+ "from "
			+ "settle_record "
			+ "where "
			+ "busy_Date>=#{start} and busy_Date<=#{end}  GROUP BY name ")
	ArrayList<IndexPerformance> getAllBusyTypeProformance(
			@Param("start") String start, @Param("end") String end,
			@Param("products") ArrayList<String> products);

	/**
	 * 取得所有的经营单位在指定时间段内的国际结算量
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<UnitPerformance> 创建时间：2019年8月4日
	 */
	@Select("<script>"
			+ "select "
			+ "select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "belong_branch_code as code "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "group by code "
			+ "</script>")
	ArrayList<UnitPerformance> getAllUnitPerformance(
			@Param("start") String start, @Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * 取得每月的业务量
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @return List<IndexPerformance> 创建时间：2019年6月11日 @
	 */
	@Select("<script> select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times, "
			+ "left(busy_date,6) as name "
			+ "from settle_record "
			+ " where "
			+ "product_name in  "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by name asc "
			+ "</script>")
	List<IndexPerformance> getMonthPerformance(@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： 取得所有的业务统计 </br>
	 * @return List<SettleRange> 创建时间：2019年6月13日
	 */
	@Select("select *  from settle_range")
	List<SettleRange> getsettleRange();

	/**
	 * 取得所有国际结算统计口径内的产品信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： 取得所有国际结算统计口径内的产品信息 </br>
	 * @return List<String> 创建时间：2019年6月13日
	 */
	@Select("select *  from settle_record")
	List<String> getProductInRange();

	@Select("select distinct product_name as name  from settle_record  ")
	ArrayList<String> getAllProdects();

	/**
	 * 保存国际结算量的统计口径产品
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param products
	 * @return int 创建时间：2019年 7月7日
	 */
	@Insert("<script> "
			+ "Insert into settle_product_information"
			+ " (name,isSettleRange) values "
			+ "<foreach collection='products' item='item' separator=',' > "
			+ "( #{item},1)"
			+ "</foreach> "
			+ " </script>")
	int saveSettleRangeProduct(@Param("products") ArrayList<String> products);

	/**
	 * 从国际业务流水表中取得所有的业务产品种类信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<String> 创建时间：2019年7月8日
	 */
	@Select("select distinct product_name as name  from settle_record  ")
	ArrayList<String> getAllProductFromSettleRecord();

	/**
	 * 从国际业务产品表中读取所有的参与国际结算统计的外汇业务产品信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<Product> 创建时间：2019年7月8
	 */
	@Select("select *  from product where settleRange='true' ")
	ArrayList<Product> getSettleRangeProduct();

	/**
	 * 取得所有国际结算量产品的分类统计
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @param productName
	 * @return List<IndexPerformance> 创建时间：2019年7月23日
	 */
	@Select("<script> select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times, "
			+ "product_name as name "
			+ "from settle_record "
			+ " where "
			+ "product_name in  "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getAllBusySettleTypeProformance(
			@Param("start") String start, @Param("end") String end,
			@Param("products") ArrayList<String> products);

	/**
	 * 取得所有的经营单位名称
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<String> 创建时间：2019年7月26日
	 */
//	@Select("<script>select distinct belong_branch_name as name from settle_record where busy_date&gt;='20180920' </script>")
	@Select("<script>select distinct belong_branch_name as name from settle_record  </script>")
	ArrayList<String> getAllUnitName();

	/**
	 * 取得所有的经营单位的完整信息
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @return ArrayList<Unit> 创建时间：2019年7月27日
	 */
	@Select("select "
			+ "distinct belong_branch_name as name,"
			+ "belong_branch_code as id "
			+ "from "
			+ "settle_record ")
	ArrayList<Unit> getAllUnitInfo();

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @return ArrayList<IndexPerformance> 创建时间：2019年7月28日
	 */
	@Select("<script> select sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times, "
			+ "product_name as name "
			+ "from settle_record "
			+ " where "
			+ "product_name in  "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getSettleTypeProformanceByDate(
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @param product
	 * @return ArrayList<SettleRecord> 创建时间：2019年7月29日
	 */
	@Select("<script> "
			+ "select * from settle_record where "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ "and "
			+ "product_name = #{product}"
			+ "</script>")
	ArrayList<SettleRecord> getDateRangeDetail(@Param("start") String start,
			@Param("end") String end,
			@Param("product") String product);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end void 创建时间：2019年7月30日
	 */
	@Select("<script> "
			+ "select "
			+ "product_Name as name,"
			+ "sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ " cust_Name =#{unit.name} "
			+ " and "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getClientIndexPerformance(
			@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);

	@Select("<script> "
			+ "select "
			+ "belong_branch_Name ,"
			+ "product_Name as name,"
			+ "sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ " belong_branch_Name =#{unit.name} "
			+ " and "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by performance desc "
			+ "</script>")
	ArrayList<IndexPerformance> getUnitIndexPerformance(
			@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);

	/**
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param start
	 * @param end
	 * @return ArrayList<MonthPerformace> 创建时间：2019年8月3日
	 */
	@Select("<script> "
			+ "select "
			+ "belong_branch_Name ,"
			+ "left(busy_date,6) as name ,"
			+ "product_Name ,"
			+ "sum(busy_amount * usd_rate)/10000 as performance,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ " belong_branch_Code =#{unit.id} "
			+ " and "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by name "
			+ " order by name desc "
			+ "</script>")
	ArrayList<MonthPerformace> getUnitMonthPerformace(@Param("unit") Unit unit,
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param expiry
	 * @return ArrayList<UnitTask> 创建时间：2019年8月4日
	 */
	@Select("select * from settle_task where expiry=#{expiry} ")
	ArrayList<HashMap<String, Object>> getUnitTasks(
			@Param("expiry") String expiry);

	@Select("select * from settle_task_percent where expiry=#{expiry} and season=#{season}")
	ArrayList<HashMap<String, Object>> getUnitTasks_season(
			@Param("expiry") String expiry, @Param("season") int season);

	/**
	 * 取得经营单位的国际结算量实绩（用于填充国际结算业务任务完成率表）
	 * 
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param start
	 * @param end
	 * @param products
	 * @return ArrayList<UnitPerformance> 创建时间：2019年8月10日
	 */
	@Select("<script>"
			+ "select belong_branch_name as branchName,"
			+ "belong_branch_code as branchCode,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by branchCode "
			+ " order by branchCode desc "
			+ "</script>")
	ArrayList<HashMap<String, Object>> getUnitPerformance(
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param compareDate
	 * @param compareDate2
	 * @param products
	 * @return ArrayList<HashMap<String,Object>> 创建时间：2019年8月25日
	 */
	@Select("<script>"
			+ "select belong_branch_name as branchName,"
			+ "belong_branch_code as branchCode,"
			+ "cust_name as custName,"
			+ "cust_number as custCode,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by custCode "
			+ " order by branchCode desc "
			+ "</script>")
	ArrayList<HashMap<String, Object>> getClientPerformance(
			@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param compareDate
	 * @param end
	 * @return ArrayList<HashMap<String,Object>> 创建时间：2019年8月25日
	 */
	@Select("<script>"
			+ "select cust_Name as custName,"
			+ "cust_number as custCode,"
			+ "belong_branch_code as branchCode,"
			+ "belong_branch_name as branchName "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "cust_type=#{clientType} "
			+ " group by custCode "
			+ " order by branchCode desc "
			+ "</script>")
	ArrayList<HashMap<String, Object>> getClients(@Param("start") String start,
			@Param("end") String end,
			@Param("products") ArrayList<Product> products,
			@Param("clientType") String clietnType);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end void 创建时间：2019年8月31日
	 */
	@Select("<script>"
			+ "select "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "belong_branch_code=${unit.getId}"
			+ " group by month "
			+ " order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getUnitSettleMonthPerformance(@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);

	/**
	 * @author linzhenhuan </br>
	 * 方法说明： </br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end void 创建时间：2019年8月31日
	 */
	@Select("<script>"
			+ "select "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " and "
			+ "cust_number=${unit.getId}"
			+ " group by month "
			+ " order by month asc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getClientSettleMonthPerformance(@Param("unit") Unit unit,
			@Param("products") ArrayList<Product> products,
			@Param("start") String start, @Param("end") String end);
	/**
	 * @author linzhenhuan  </br>
	 *　方法说明：　　　　　　　　　　　</br>
	 * @param unit
	 * @param products
	 * @param start
	 * @param end
	 * @return
	 *ArrayList<LinkedHashMap<String,Object>>
	 * 创建时间：2019年8月31日
	 */
	@Select("<script>"
			+ "select "
			+ "id as name , "
			+ "left(busy_date,6) as month ,"
			+ "sum(busy_amount * usd_rate)/10000 as amount,"
			+ "count(busy_amount) as times "
			+ "from settle_record "
			+ "where "
			+ "product_Name in "
			+ "<foreach collection='products' item='item' open='(' close=')' separator=','>"
			+ "'${item.getName}'"
			+ "</foreach> "
			+ " and "
			+ "busy_date&gt;=${start} and busy_date&lt;=${end} "
			+ " group by month "
			+ " order by month desc "
			+ "</script>")
	ArrayList<LinkedHashMap<String, Object>> getTotalSettleMonthPerformance(
			@Param("unit")Unit unit, @Param("products")ArrayList<Product> products, @Param("start")String start, @Param("end")String end);

}
